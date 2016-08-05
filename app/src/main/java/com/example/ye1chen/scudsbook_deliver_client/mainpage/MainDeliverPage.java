package com.example.ye1chen.scudsbook_deliver_client.mainpage;

import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ye1chen.scudsbook_deliver_client.HttpConnection;
import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;
import com.example.ye1chen.scudsbook_deliver_client.R;
import com.example.ye1chen.scudsbook_deliver_client.ScudsbookConstants;
import com.example.ye1chen.scudsbook_deliver_client.ScudsbookUtil;
import com.example.ye1chen.scudsbook_deliver_client.UserInfo;
import com.example.ye1chen.scudsbook_deliver_client.database.ScudsbookDba;
import com.example.ye1chen.scudsbook_deliver_client.location.LocationDetector;
import com.example.ye1chen.scudsbook_deliver_client.orderpage.DeliverOrderPage;
import com.example.ye1chen.scudsbook_deliver_client.orderpage.ManagerAddNewOrder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ye1.chen on 4/27/16.
 */
public class MainDeliverPage extends Activity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, OnMapReadyCallback {

    private Spinner mSpinner;
    private ListView mListView;
    private LinearLayout mMapView;
    private MapFragment mapFragment;
    private GoogleMap mMap;
    MainListAdapter mAdapter;
    private static final int DEFAULT_ZOOM_LEVEL = 13;
    private static final int DEFAULT_MAP_ANIMATION_DURATION = 1000;

    private ContentObserver mDbObserver = new ContentObserver(new Handler()) {

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            updateListView();
        }

        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setSpinner();
        setListView();
        setMapView();
        if(!UserInfo.getInstance(this).isManagerManager()) {
            LocationDetector.getInstance(this).startLocationUpdate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListView();
        resetSpinner();
    }

    private void setSpinner() {
        mSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_of_view_selector_manager, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
        mSpinner.setSelection(1);
    }

    private void setListView() {
        mListView = (ListView) findViewById(R.id.lv_main_page);
        mAdapter = new MainListAdapter(this);
        mAdapter.setData(null);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainDeliverPage.this, ManagerAddNewOrder.class));
                break;
            case 1:
                //default page
                setViewVisible(true,false);
                break;
            case 2:
                break;
            case 3:
                setViewVisible(false,true);
                break;
            default:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainDeliverPage.this, DeliverOrderPage.class);
        intent.putExtra(DeliverOrderPage.INTENT_EXTRA_KEY_ORDER_ID, ((OrderInfo) mAdapter.getItem(position)).getId());
        startActivity(intent);
    }

    private void setViewVisible(boolean listStae, boolean mapState) {
        mListView.setVisibility(listStae? View.VISIBLE : View.GONE);
        mMapView.setVisibility(mapState? View.VISIBLE : View.GONE);
        if(mapState) {
            if(!UserInfo.getInstance(this).isManagerManager()) {
                LocationDetector.getInstance(this).startLocationUpdate();
            } else {
                if (futureTask != null)
                    futureTask.cancel(true);
                mExecutor = new ScheduledThreadPoolExecutor(1);
                futureTask = mExecutor.schedule(new LocationUpdateTask(), delay, TimeUnit.MILLISECONDS);
            }
        } else {
            if (futureTask != null)
                futureTask.cancel(true);
            LocationDetector.getInstance(this).stopLocationUpdate();
        }
    }

    private void setMapView() {
        mMapView = (LinearLayout) findViewById(R.id.mapview);
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void moveMarkerMap(double lat, double lont) {
        if (mMap != null) {
            mMap.clear();
            LatLng latLng = new LatLng(lat, lont);
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .draggable(false));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM_LEVEL),
                    DEFAULT_MAP_ANIMATION_DURATION, null);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private int delay = 5000;
    private ScheduledFuture<?> futureTask;
    private ScheduledThreadPoolExecutor mExecutor;

    private class LocationUpdateTask implements Runnable {
        String tempResult;
        @Override
        public void run() {
            HashMap<String, String> tempMap = new HashMap<>();
            tempMap.put(ScudsbookConstants.key_scudsbook, getResources().getString(R.string.key_connection));
            tempMap.put(ScudsbookConstants.key_type, ScudsbookConstants.type_location_query);
            tempMap.put(ScudsbookConstants.user_name, "chenyemahe@gmail.com");
            tempResult = HttpConnection.postRequest(tempMap, 10000, 15000);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(TextUtils.equals(tempResult, "error:no_user"))
                        return;
                    if(!TextUtils.isEmpty(tempResult)) {
                        String latLan[] = tempResult.split(",");
                        moveMarkerMap(Double.parseDouble(latLan[0]), Double.parseDouble(latLan[1]));
                    }
                }
            });
            futureTask = mExecutor.schedule(new LocationUpdateTask(), delay, TimeUnit.MILLISECONDS);
        }
    }

    private class OrderInfoListQuery implements Runnable {

        @Override
        public void run() {
            final ArrayList<OrderInfo> list = ScudsbookUtil.getOrderInfoListFromServerDeliver(MainDeliverPage.this);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ScudsbookDba.table_size = list.size();
                    mAdapter.setData(list);
                    mAdapter.notifiListUpdate();
                }
            });
        }
    }

    private void updateListView() {
        new Thread(new OrderInfoListQuery()).start();
        //ArrayList<OrderInfo> list = (ArrayList<OrderInfo>) ScudsbookDba.getDB().getAllOrder(getContentResolver());
        //ScudsbookDba.table_size = list.size();
        //mAdapter.setData(list);
        //mAdapter.notifiListUpdate();
    }

    private void resetSpinner() {
        if(mSpinner.getSelectedItemPosition() != 3)
            mSpinner.setSelection(1);
    }
}
