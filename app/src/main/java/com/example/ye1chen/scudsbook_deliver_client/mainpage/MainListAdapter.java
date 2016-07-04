package com.example.ye1chen.scudsbook_deliver_client.mainpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.example.ye1chen.scudsbook_deliver_client.R;

/**
 * Created by ye1.chen on 4/28/16.
 */
public class MainListAdapter extends BaseAdapter{

    private Context mContext;

    public MainListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_current_order, parent, false);
        }
        return convertView;
    }

}
