package com.example.ye1chen.scudsbook_deliver_client.location;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ye1chen.scudsbook_deliver_client.R;

/**
 * Created by ye1.chen on 2/23/16.
 */
public class LocationActivity extends Activity implements View.OnClickListener{


    private Button mLocation;
    private TextView mLatLan;
    private LocationManager mLocationManager;

    private static final long LOCATION_UPDATE_MIN_TIME = 5000; // 5 second
    private static final long LOCATION_UPDATE_MIN_DISTANCE = 5; // 5 meters

    private PowerManager.WakeLock wl;

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLatLan.append("\n" + location.getLatitude() + " " + location.getLongitude());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        mLatLan = (TextView) findViewById(R.id.textView);
        mLocation = (Button) findViewById(R.id.button);
        mLocation.setOnClickListener(this);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        wl = ((PowerManager)getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getLocalClassName());
        wl.acquire();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button) {
            requestLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wl != null && wl.isHeld()) {
            wl.release();
        }
    }

    /**
     * Fire request for new location update.
     */
    private void requestLocationUpdates() {

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
            Log.w(this.getLocalClassName(), "Location access denied, which should not be happening: " + se.toString());
        }
    }
}
