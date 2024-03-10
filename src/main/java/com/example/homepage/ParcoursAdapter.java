package com.example.homepage;

import com.example.homepage.Favorite;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.homepage.R;

public class ParcoursAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;
    private String[] parcours;

    public ParcoursAdapter(Context context, int resource, String[] parcours) {
        super(context, resource, parcours);
        inflater = LayoutInflater.from(context);
        this.parcours = parcours;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.liste_item_parcours, parent, false);

            holder = new ViewHolder();
            holder.parcoursTextView = convertView.findViewById(R.id.parcours_text_view);
            holder.favoriteImageView = convertView.findViewById(R.id.favorite_image_view);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String parcoursItem = parcours[position];

        // Définir le texte du parcours
        holder.parcoursTextView.setText(parcoursItem);

        // Gérer le clic sur l'icône de cœur
        holder.favoriteImageView.setOnClickListener(v -> {
            // Ajouter le parcours à la classe Favorite
            Favorite.addFavorite(parcoursItem);
        });

        return convertView;
    }

    private class ViewHolder {
        TextView parcoursTextView;
        ImageView favoriteImageView;
    }
}
