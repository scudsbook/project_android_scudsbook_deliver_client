package com.example.ye1chen.scudsbook_deliver_client.orderpage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;
import com.example.ye1chen.scudsbook_deliver_client.R;
import com.example.ye1chen.scudsbook_deliver_client.ScudsbookUtil;
import com.example.ye1chen.scudsbook_deliver_client.database.ScudsbookDba;

/**
 * Created by ye1.chen on 4/28/16.
 */
public class OrderPage extends Activity {

    public static final String INTENT_EXTRA_KEY_ORDER_ID = "intent_extra_key_order_id";

    private String orderId;
    private LinearLayout mOrderLout;
    private OrderInfo info;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        orderId = getIntent().getStringExtra(INTENT_EXTRA_KEY_ORDER_ID);
        mOrderLout = (LinearLayout) findViewById(R.id.ly_list_item_order);
        mListView = (ListView) findViewById(R.id.item_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        info = ScudsbookDba.getDB().getOrderInfoById(getContentResolver(), orderId);
        ScudsbookUtil.setUpListItemCurrentOrderView(this, mOrderLout, info);
        setUpList();
    }

    private void setUpList() {
        if(info == null)
            return;
        String[] orderDetial = info.getOrderSum().split(",");
        mListView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,orderDetial));
    }
}
