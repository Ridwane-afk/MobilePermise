package com.example.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.homepage.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private RequestQueue requestQueue;

    private AutoCompleteTextView autoCompleteTextView;
    private HashMap<String, List<LatLng>> centerPointsMap;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private GoogleMap.OnMarkerDragListener mapListener = new GoogleMap.OnMarkerDragListener() {
        @Override
        public void onMarkerDrag(@NonNull Marker marker) {
        }

        @Override
        public void onMarkerDragEnd(@NonNull Marker marker) {
            System.out.println(marker);
        }

        @Override
        public void onMarkerDragStart(@NonNull Marker marker) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        //  mMap.setOnMarkerDragListener(mapListener);
        requestQueue = Volley.newRequestQueue(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        String selectedCentre = intent.getStringExtra("centre");

//----------------------------------------------------------------------------------------- Anderlect-------------------------------------//

        // Liste des coordonnées de points à ajouter sur la carte
        List<LatLng> anderlechtPoints = new ArrayList<>();
        anderlechtPoints.add(new LatLng(50.837548, 4.296130));
        anderlechtPoints.add(new LatLng(50.836220, 4.312136));
        anderlechtPoints.add(new LatLng(50.832745, 4.327912));
        anderlechtPoints.add(new LatLng(50.830491, 4.335228));
        anderlechtPoints.add(new LatLng(50.833074, 4.338430));
        anderlechtPoints.add(new LatLng(50.841048, 4.342502));
        anderlechtPoints.add(new LatLng(50.844415, 4.344448));
        anderlechtPoints.add(new LatLng(50.853195, 4.326225));
        anderlechtPoints.add(new LatLng(50.857196, 4.318210));
        anderlechtPoints.add(new LatLng(50.855356, 4.303645));
        anderlechtPoints.add(new LatLng(50.849926, 4.299800));
        anderlechtPoints.add(new LatLng(50.841947, 4.296438));
        anderlechtPoints.add(new LatLng(50.837548, 4.296130));


        // Ajout des marqueurs pour chaque anderlechtPoints de la liste
        for (LatLng point : anderlechtPoints) {
            mMap.addCircle(new CircleOptions().center(point).radius(33).strokeWidth(5).fillColor(Color.RED));
        }

        // Création du polyline pour relier les marqueurs(points rouge)
        PolylineOptions anderlechtPolylineOptions = new PolylineOptions()
                .addAll(anderlechtPoints)
                .color(Color.RED)
                .width(10);

        mMap.addPolyline(anderlechtPolylineOptions);

//--------------------------------------------------------------------------------------------- Evers-------------------------------------//
        // Liste des coordonnées de points à ajouter sur la carte pour Evers
        List<LatLng> eversPoints = new ArrayList<>();
        eversPoints.add(new LatLng(50.894217, 4.417100));
        eversPoints.add(new LatLng(50.895758, 4.426212));
        eversPoints.add(new LatLng(50.899810, 4.428827));
        eversPoints.add(new LatLng(50.901719, 4.423585));
        eversPoints.add(new LatLng(50.900118, 4.414703));
        eversPoints.add(new LatLng(50.895875, 4.413387));
        eversPoints.add(new LatLng(50.894217, 4.417100));


        // Ajout des marqueurs pour chaque point de la liste
        for (LatLng point : eversPoints) {
         //   mMap.addMarker(new MarkerOptions().position(point)); // Pour utiliser le marquer de google map cela permet aussi d'accédé a sa position lors du click
            mMap.addCircle(new CircleOptions().center(point).radius(27).strokeWidth(5).fillColor(Color.GREEN));
        }

        // Création du polyline pour relier les marqueurs
        PolylineOptions eversPolylineOptions = new PolylineOptions()
                .addAll(eversPoints)
                .color(Color.GREEN)
                .width(10);

        mMap.addPolyline(eversPolylineOptions);
//----------------------------------------------------------------------------------------------------- BraineLeComtePoints-------------------------------------//

        List<LatLng> BraineLeComtePoints = new ArrayList<>();
        BraineLeComtePoints.add(new LatLng(50.603675, 4.118906));
        BraineLeComtePoints.add(new LatLng(50.599152, 4.114572));
        BraineLeComtePoints.add(new LatLng(50.594144, 4.120442));
        BraineLeComtePoints.add(new LatLng(50.589109, 4.115880));
        BraineLeComtePoints.add(new LatLng(50.588407, 4.105246));
        BraineLeComtePoints.add(new LatLng(50.582461, 4.098779));
        BraineLeComtePoints.add(new LatLng(50.579410, 4.103216));
        BraineLeComtePoints.add(new LatLng(50.578782, 4.110344));
        BraineLeComtePoints.add(new LatLng(50.583089, 4.116639));
        BraineLeComtePoints.add(new LatLng(50.585992, 4.124634));
        BraineLeComtePoints.add(new LatLng(50.589650, 4.126174));
        BraineLeComtePoints.add(new LatLng(50.594106, 4.134069));
        BraineLeComtePoints.add(new LatLng(50.599932, 4.139958));
        BraineLeComtePoints.add(new LatLng(50.602221, 4.137711));
        BraineLeComtePoints.add(new LatLng(50.604191, 4.126017));
        BraineLeComtePoints.add(new LatLng(50.605329, 4.118726));


        // Ajout des marqueurs pour chaque point de la liste
        for (LatLng point : BraineLeComtePoints) {
            //   mMap.addMarker(new MarkerOptions().position(point)); // Pour utiliser le marquer de google map cela permet aussi d'accédé a sa position lors du click
            mMap.addCircle(new CircleOptions().center(point).radius(27).strokeWidth(5).fillColor(Color.GREEN));
        }

        // Création du polyline pour relier les marqueurs
        PolylineOptions braineComtePointsPolylineOptions = new PolylineOptions()
                .addAll(BraineLeComtePoints)
                .color(Color.GREEN)
                .width(10);

        mMap.addPolyline(braineComtePointsPolylineOptions);




        if (selectedCentre != null) {
            switch (selectedCentre) {
                case "Anderlecht":
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(anderlechtPoints.get(2), 13)); // Permet de Centrer la carte sur le 2eme point et d'ajuster le niveau de zoom
                    LatLng startPoint = new LatLng(50.839098, 4.329651);
                    LatLng endPoint = new LatLng(50.863134, 4.346799);
                    getDirections(startPoint, endPoint);
                    break;
                case "Evers":
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eversPoints.get(0), 14));  // Permet de Centrer la carte sur le premier point et d'ajuster le niveau de zoom
                    LatLng eversStartPoint = new LatLng(50.871031, 4.307105);
                    LatLng eversEndPoint = new LatLng(50.864865, 4.295778);
                    getDirections(eversStartPoint, eversEndPoint);
                    break;
                case "Braine le Comte":
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BraineLeComtePoints.get(2), 13));  // Permet de Centrer la carte sur le premier point et d'ajuster le niveau de zoom
                    LatLng braineComteStartPoint = new LatLng(50.603675, 4.118906);
                    LatLng braineComteEndPoint = new LatLng(50.605329, 4.118726);
                    getDirections(braineComteStartPoint, braineComteEndPoint);
                    break;
                default:
                    break;
            }
        }

    }


//--------------------------       Utilisation API Google map ------------------------------------------------//

    private void getDirections(LatLng origin, LatLng destination) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + origin.latitude + "," + origin.longitude +
                "&destination=" + destination.latitude + "," + destination.longitude +
                "&mode=driving" +
                "&key=" + "AIzaSyDu3je2rtM-ljorGmlLicaYied8vrLBon8";

        JsonObjectRequest request = new JsonObjectRequest(url, null, response -> {
            try {
                JSONArray steps = response.getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONArray("legs")
                        .getJSONObject(0)
                        .getJSONArray("steps");

                List<LatLng> polylinePoints = new ArrayList<>();

                for (int i = 0; i < steps.length(); i++) {
                    JSONObject step = steps.getJSONObject(i);
                    String polyline = step.getJSONObject("polyline").getString("points");
                    polylinePoints.addAll(PolyUtil.decode(polyline));
                }

                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(polylinePoints)
                        .color(Color.BLUE)
                        .width(10);

                mMap.addPolyline(polylineOptions);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        });

        requestQueue.add(request);
    }
}