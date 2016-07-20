package com.example.ye1chen.scudsbook_deliver_client.mainpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;
import com.example.ye1chen.scudsbook_deliver_client.R;

import java.util.ArrayList;

/**
 * Created by ye1.chen on 4/28/16.
 */
public class MainListAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<OrderInfo> orderList;

    public MainListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_current_order, parent, false);
        }
        MainListViewHolder mHolder = new MainListViewHolder(mContext, convertView, orderList.get(position));
        convertView.setTag(mHolder);
        return convertView;
    }

    public void setData(ArrayList<OrderInfo> list) {
        orderList = list;
        if(orderList == null)
            orderList = new ArrayList<>();
    }

    public void notifiListUpdate() {
        notifyDataSetChanged();
    }
}
