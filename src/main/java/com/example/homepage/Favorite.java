package com.example.homepage;

import java.util.ArrayList;
import java.util.List;

public class Favorite {
    private static List<String> favorisList;

    public Favorite() {
        favorisList = new ArrayList<>();
    }

    public static void addFavorite(String parcours) {
        if (!favorisList.contains(parcours)) {
            favorisList.add(parcours);
        }
    }

    public void removeFavorite(String parcours) {
        favorisList.remove(parcours);
    }

    public List<String> getFavorites() {
        return favorisList;
    }

    public boolean isFavorite(String parcours) {
        return favorisList.contains(parcours);
    }
}

