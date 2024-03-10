package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.homepage.databinding.ActivityCentreOptionBinding;

public class ChoixParcoursActivity extends AppCompatActivity {

    private TextView autoCompleteTextView;

    private ListView listView;
    private String selectedCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_parcours);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        listView = findViewById(R.id.listView);

        selectedCentre = getIntent().getStringExtra("centre");

        autoCompleteTextView.setText("Choisissez un parcours pour le centre d'examen de " + selectedCentre);

        switch (selectedCentre) {
            case "Anderlecht":
            case "Evers":
            case "Braine le Comte":
                String[] andParcours = {"Parcours 1", "Parcours 2", "Parcours 3"};
                ArrayAdapter<String> andAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, andParcours);
                listView.setAdapter(andAdapter);
                break;
            default:
                autoCompleteTextView.setText("Le centre d'examen sélectionné n'est pas valide.");
                break;
        }

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedParcours = parent.getItemAtPosition(position).toString();
            Intent intent = new Intent(ChoixParcoursActivity.this, MapsActivity.class);
            intent.putExtra("centre", selectedCentre);
            intent.putExtra("parcours", selectedParcours);
            startActivity(intent);
        });
    }
}
