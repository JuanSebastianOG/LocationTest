package com.example.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private List<Double> lats;
    private List<Double> longis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, "pk.eyJ1IjoianNlYmFzdGlhbm9nIiwiYSI6ImNrNDJiMGhydDBhOHUzbHBnZzBtaHJtb3QifQ.4ZCPDQjt9bNMyHnymT618Q");
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        lats=new ArrayList<>();
        longis=new ArrayList<>();

        Query query = FirebaseDatabase.getInstance().getReference("track");
        query.addValueEventListener(valueEventListener);

    }

    ValueEventListener valueEventListener= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            lats.clear();
            longis.clear();


            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Track track = snapshot.getValue(Track.class);
                    lats.add(track.getLat());
                    longis.add(track.getLongi());
                }

            }
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                    mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            // Map is set up and the style has loaded. Now you can add data or make other map adjustments.

                            mapboxMap.clear();
                            for(int i=0;i<lats.size();i++){
                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(lats.get(i),longis.get(i)))
                                        .title("Eiffeliug Tower")
                                        .snippet("holi")
                                        .icon(IconFactory.getInstance(getApplicationContext()).fromResource(R.drawable.ic_action_name))
                                );
                            }
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng( 4.650257499888539,  -4.650257499888539))
                                    .title("Eiffel Tower")
                                    .snippet("holi"));

                        }

                    });
                }
            });

        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
