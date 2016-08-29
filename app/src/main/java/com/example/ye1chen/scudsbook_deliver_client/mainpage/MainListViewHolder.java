package com.example.ye1chen.scudsbook_deliver_client.mainpage;

import android.content.Context;
import android.view.View;

import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;
import com.example.ye1chen.scudsbook_deliver_client.ScudsbookUtil;
import com.example.ye1chen.scudsbook_deliver_client.database.ScudsbookDbUtil;

/**
 * Created by ye1.chen on 7/20/16.
 */
public class MainListViewHolder {

    public MainListViewHolder(Context context, View v, OrderInfo info) {
        ScudsbookUtil.setUpListItemCurrentOrderView(context, v, info);
    }


}
