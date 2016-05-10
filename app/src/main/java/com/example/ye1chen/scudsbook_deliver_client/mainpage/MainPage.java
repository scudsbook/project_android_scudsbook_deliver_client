package com.example.ye1chen.scudsbook_deliver_client.mainpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ye1chen.scudsbook_deliver_client.R;
import com.example.ye1chen.scudsbook_deliver_client.orderpage.OrderPage;
import com.google.android.gms.maps.MapFragment;

/**
 * Created by ye1.chen on 4/27/16.
 */
public class MainPage extends Activity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private Spinner mSpinner;
    private ListView mListView;
    private LinearLayout mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setSpinner();
        setListView();
        setMapView();
    }

    private void setSpinner() {
        mSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_of_view_selector, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    private void setListView() {
        mListView = (ListView) findViewById(R.id.lv_main_page);
        MainListAdapter mAdapter = new MainListAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                setViewVisible(true,false);
                break;
            case 1:
                break;
            case 2:
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
        startActivity(new Intent(MainPage.this, OrderPage.class));
    }

    private void setViewVisible(boolean listStae, boolean mapState) {
        mListView.setVisibility(listStae? View.VISIBLE : View.GONE);
        mMapView.setVisibility(mapState? View.VISIBLE : View.GONE);

    }

    private void setMapView() {
        mMapView = (LinearLayout) findViewById(R.id.mapview);
    }
}
