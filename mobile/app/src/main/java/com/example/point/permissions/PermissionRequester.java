package com.example.point.permissions;

import android.Manifest;
import android.os.Build;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class PermissionRequester {
    private static final String tag = "{PERMISSION REQUESTER}";

    private final AppCompatActivity context;

    public PermissionRequester(AppCompatActivity context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void requestLocationPermissions() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                context.registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            Boolean backgroundLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION, false);
                            if(backgroundLocationGranted != null && backgroundLocationGranted)
                                Log.i(tag, "Background location permission granted!");
                            if (fineLocationGranted != null && fineLocationGranted) {
                                Log.i(tag, "Fine location permissions granted!");
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                Log.i(tag, "Coarse location permissions granted!");
                            } else {
                                Log.e(tag, "Permissions denied!");
                            }
                        }
                );
        locationPermissionRequest.launch(new String[]
                {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                });
    }
}
