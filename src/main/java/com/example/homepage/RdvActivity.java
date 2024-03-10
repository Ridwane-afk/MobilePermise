package com.example.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class RdvActivity extends AppCompatActivity {

    private EditText searchBar;
    private ImageButton searchButton;
    private Map<String, String> centers;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    //revenir dans la page d'accueil
                    Intent intentm = new Intent(RdvActivity.this, MainActivity.class);
                    startActivity(intentm);
                    return true;
                case R.id.menuFavorit:
                    // TODO: Handle item2 click
                    return true;
                case R.id.menuAccount:
                    // Ouvrir la page d'ajout de photo de profil
                    Intent intent = new Intent(RdvActivity.this, ProfilePhotoActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.menuSetting:
                    // TODO: Handle item4 click
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv);

        searchBar = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.search_button);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCenters();
            }
        });

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCenters();
                    return true;
                }
                return false;
            }
        });

        centers = new HashMap<>();
        centers.put("Anderlecht", "https://www.securiteautomobile.be/Rdv_pc");
        centers.put("Schaerbeek", "https://www.autocontrole.be/fr/rendez-vous-permis-de-conduire");
        centers.put("Braine le comte", "https://www.aibv.be/fr/prendre-rendez-vous-permis-de-conduire");
        // Ajout autres centres d'examen possible ici...
    }

    private void searchCenters() {
        String query = searchBar.getText().toString().trim();
        if (!query.isEmpty()) {
            String url = centers.get(query);
            if (url != null) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Aucun centre d'examen trouvé pour la recherche : " + query, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Veuillez entrer un nom de centre d'examen valide", Toast.LENGTH_SHORT).show();
        }
    }
}


