package com.example.point;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class LocationService extends Service {
    private static final String tag = "{LOCATION SERVICE}";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(tag, "Location service");
        return null;
    }
}
