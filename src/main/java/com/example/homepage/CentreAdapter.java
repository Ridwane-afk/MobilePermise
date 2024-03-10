package com.example.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homepage.databinding.ActivityCentreOptionBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CentreAdapter extends AppCompatActivity {

    private ActivityCentreOptionBinding binding;
    private AutoCompleteTextView autoCompleteTextView;
    boolean centreExiste = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCentreOptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        autoCompleteTextView = binding.autoCompleteTextView1;

        List<String> centres = new ArrayList<>(Arrays.asList("Anderlecht", "Evers", "Braine le Comte"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, centres);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCentre = parent.getItemAtPosition(position).toString();
            Intent intent = new Intent(CentreAdapter.this, ChoixParcoursActivity.class);

            // Vérifiez si le centre sélectionné existe dans la liste de centres
            if (centres.contains(selectedCentre)) {
                centreExiste = true;
                intent.putExtra("centre", selectedCentre);
                startActivity(intent);
            } else {
                Toast.makeText(CentreAdapter.this, "Le centre sélectionné n'existe pas.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
