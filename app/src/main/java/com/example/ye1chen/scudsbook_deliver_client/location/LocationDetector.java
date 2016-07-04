package com.example.ye1chen.scudsbook_deliver_client.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.ye1chen.scudsbook_deliver_client.HttpConnection;
import com.example.ye1chen.scudsbook_deliver_client.R;
import com.example.ye1chen.scudsbook_deliver_client.ScudsbookConstants;
import com.example.ye1chen.scudsbook_deliver_client.UserInfo;

import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ye1.chen on 5/18/16.
 */
public class LocationDetector {

    private static final long LOCATION_UPDATE_MIN_TIME = 5000; // 5 second
    private static final long LOCATION_UPDATE_MIN_DISTANCE = 5; // 5 meters

    private PowerManager.WakeLock wl;
    private LocationManager mLocationManager;
    private Context mContext;
    private static LocationDetector mDetector;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            new Thread(new LocationUpdateTask(location)).start();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private LocationDetector(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        wl = ((PowerManager)mContext.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().toString());
    }

    public static LocationDetector getInstance(Context context) {
        if(mDetector == null) {
            mDetector = new LocationDetector(context);
        }
        return mDetector;
    }

    public void startLocationUpdate() {
        wl.acquire();
        String locProvider = null;
        if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locProvider = LocationManager.NETWORK_PROVIDER;
        } else if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locProvider = LocationManager.GPS_PROVIDER;
        } else {
            locProvider = LocationManager.PASSIVE_PROVIDER;
        }

        try {
            mLocationManager.requestLocationUpdates(locProvider, LOCATION_UPDATE_MIN_TIME,
                    LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
        } catch (SecurityException se) {
            Log.w(getClass().toString(), "Location access denied, which should not be happening: " + se.toString());
        }
    }

    public void stopLocationUpdate() {
        if (wl != null && wl.isHeld()) {
            wl.release();
        }
        try {
            mLocationManager.removeUpdates(mLocationListener);
        } catch (SecurityException se) {
            Log.w(getClass().toString(), "Location access denied, which should not be happening: " + se.toString());
        }
    }

    private class LocationUpdateTask implements Runnable {
        String tempResult;
        Location location;

        public LocationUpdateTask(Location loc) {
            location = loc;
        }
        @Override
        public void run() {
            HashMap<String, String>mMap = new HashMap<>();
            mMap.put(ScudsbookConstants.key_scudsbook, mContext.getResources().getString(R.string.key_connection));
            mMap.put(ScudsbookConstants.key_type, ScudsbookConstants.type_location_update);
            mMap.put(ScudsbookConstants.user_name, UserInfo.getInstance(mContext).getUserName());
            mMap.put(ScudsbookConstants.location_lan, String.valueOf(location.getLongitude()));
            mMap.put(ScudsbookConstants.location_lat, String.valueOf(location.getLatitude()));
            HttpConnection.postRequest(mMap,5000,5000);
        }
    }
}
