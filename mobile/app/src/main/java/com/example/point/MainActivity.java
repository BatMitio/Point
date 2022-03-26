package com.example.point;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String tag = "{MAIN ACTIVITY}";
    private boolean locationUpdatesRunning = false;
    private final PointLocationProvider pointLocationProvider;

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationUpdatesRunning) {
            pointLocationProvider.stopLocationUpdates();
            locationUpdatesRunning = false;
        }
    }
}