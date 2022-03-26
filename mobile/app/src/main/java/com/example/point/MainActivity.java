package com.example.point;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String tag = "{MAIN ACTIVITY}";
    private boolean locationUpdatesRunning = false;
    private final PointLocationProvider pointLocationProvider;
    private GoogleMap map;

    public MainActivity() {
        this.pointLocationProvider = new PointLocationProvider(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.locationToggle).setOnClickListener(view -> {
            locationUpdatesRunning = !locationUpdatesRunning;
            if(locationUpdatesRunning)
                pointLocationProvider.startLocationUpdates();
            else
                pointLocationProvider.stopLocationUpdates();
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationUpdatesRunning) {
            pointLocationProvider.stopLocationUpdates();
            locationUpdatesRunning = false;
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.map = googleMap;
    }

    public GoogleMap getMap() {
        return map;
    }
}