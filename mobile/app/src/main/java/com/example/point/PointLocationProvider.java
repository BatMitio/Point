package com.example.point;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.point.permissions.PermissionRequester;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PointLocationProvider {
    private final MainActivity context;
    private final PermissionRequester permissionRequester;
    private FusedLocationProviderClient fusedLocationClient;

    private Location location;

    private final static String tag = "[PointLocationProvider]";
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            if(locationResult.getLastLocation().getAccuracy() < 10) {
                location = locationResult.getLastLocation();
                Log.i(tag, location.getLatitude() + " " + location.getLongitude() + " acc: " + location.getAccuracy());
                if(context.getMap() != null) {
                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()));
                    context.getMap().clear();
                    context.getMap().addMarker(markerOptions);
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(markerOptions.getPosition())
                            .zoom(19.0f)
                            .bearing(0.0f)
                            .tilt(30.0f)
                            .build();
                    context.getMap().animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        }
    };

    public PointLocationProvider(MainActivity context) {
        this.context = context;
        this.permissionRequester = new PermissionRequester(this.context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startLocationUpdates() {
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionRequester.requestLocationPermissions();
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
        location = null;
    }

    public Location getLocation() {
        return location;
    }
}
