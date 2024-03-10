package com.example.homepage;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfilePhotoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button addPhotoButton;
    private Button deletePhotoButton;
    private ImageView profileImageView;

    private FirebaseFirestore firestore;
    private Uri imageUri;
    private StorageReference storageReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo);

        addPhotoButton = findViewById(R.id.addPhotoButton);
        deletePhotoButton = findViewById(R.id.deletePhotoButton);
        profileImageView = findViewById(R.id.profileImageView);

        storageReference = FirebaseStorage.getInstance().getReference("profile_photos");

        // Initialisez référence Firestore ;
        firestore = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            userId = currentUser.getUid();
        } else {
            // L'utilisateur n'est pas connecté, gérez cette situation
        }

        addPhotoButton.setOnClickListener(view -> openFileChooser());
        deletePhotoButton.setOnClickListener(view -> deleteProfilePhoto());

        // Chargez l'image de profil de l'utilisateur lors de l'ouverture de l'activité
        loadProfilePhoto();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Sélectionner une image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erreur lors du chargement de l'image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        if (imageUri != null) {
            String fileName = System.currentTimeMillis() + "." + getFileExtension(imageUri);
            StorageReference fileReference = storageReference.child(fileName);

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(ProfilePhotoActivity.this, "Image de profil enregistrée avec succès", Toast.LENGTH_SHORT).show();

                        // Ajoutez le nom du fichier dans Firestore
                        Map<String, Object> data = new HashMap<>();
                        data.put("fileName", fileName);
                        firestore.collection("profile_photos").document(userId)
                                .set(data)
                                .addOnSuccessListener(aVoid -> Toast.makeText(ProfilePhotoActivity.this, "Nom du fichier enregistré avec succès dans Firestore", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(ProfilePhotoActivity.this, "Erreur lors de l'enregistrement du nom du fichier dans Firestore", Toast.LENGTH_SHORT).show());
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ProfilePhotoActivity.this, "Erreur lors de l'enregistrement de l'image de profil", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Aucune image sélectionnée", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void deleteProfilePhoto() {
        // Récupérez le nom du fichier de l'image de profil depuis Firestore
        firestore.collection("profile_photos").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String fileName = documentSnapshot.getString("fileName");
                        StorageReference fileReference = storageReference.child(fileName);

                        // Supprimez l'image de profil de Firebase Storage
                        fileReference.delete()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(ProfilePhotoActivity.this, "Image de profil supprimée avec succès", Toast.LENGTH_SHORT).show();
                                    profileImageView.setImageDrawable(null); // Supprimez l'image du ImageView
                                })
                                .addOnFailureListener(e -> Toast.makeText(ProfilePhotoActivity.this, "Erreur lors de la suppression de l'image de profil", Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(ProfilePhotoActivity.this, "Aucune image de profil à supprimer", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(ProfilePhotoActivity.this, "Erreur lors de la récupération du nom de fichier depuis Firestore", Toast.LENGTH_SHORT).show());
    }

    private void loadProfilePhoto() {
        if (userId != null) {
            firestore.collection("profile_photos").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String fileName = documentSnapshot.getString("fileName");
                            StorageReference fileReference = storageReference.child(fileName);

                            // Chargez l'image de profil depuis Firebase Storage
                            fileReference.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        Glide.with(ProfilePhotoActivity.this)
                                                .load(uri)
                                                .into(profileImageView);
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(ProfilePhotoActivity.this, "Erreur lors du chargement de l'image de profil", Toast.LENGTH_SHORT).show());
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(ProfilePhotoActivity.this, "Erreur lors de la récupération du nom de fichier depuis Firestore", Toast.LENGTH_SHORT).show());
        } else {
            // L'utilisateur n'est pas connecté
            Toast.makeText(ProfilePhotoActivity.this, "Vous devez être connecté pour accéder à cette fonctionnalité", Toast.LENGTH_SHORT).show();

            // on redirige vers la page de connexion
            // Intent intent = new Intent(ProfilePhotoActivity.this, LoginActivity.class);
            // startActivity(intent);

        }
    }
}

