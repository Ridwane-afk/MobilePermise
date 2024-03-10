package com.example.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private LinearLayout btnParcours;
    private LinearLayout btnRdv;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    Intent intentm = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intentm);
                    return true;
                case R.id.menuFavorit:
                    Intent intenth = new Intent(MainActivity.this, Favorite.class);
                    startActivity(intenth);
                    return true;
                case R.id.menuAccount:
                    // Ouvrir la page d'ajout de photo de profil
                    Intent intent = new Intent(MainActivity.this, ProfilePhotoActivity.class);
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
        setContentView(R.layout.activity_main);

        // Initialise Firebase
        FirebaseApp.initializeApp(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btnRdv = findViewById(R.id.layoutRdv);
        btnParcours = findViewById(R.id.layoutGoal);
        btnParcours.setOnClickListener((v) -> {
            startActivity(new Intent(MainActivity.this, CentreAdapter.class));
        });
        btnRdv.setOnClickListener((v) -> {
            startActivity(new Intent(MainActivity.this, RdvActivity.class));
        });


        // Masquer la barre de statut
       View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;

        decorView.setSystemUiVisibility(uiOptions);
        //



    }
}