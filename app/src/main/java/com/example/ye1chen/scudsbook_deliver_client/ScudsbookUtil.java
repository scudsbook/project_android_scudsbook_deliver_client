package com.example.ye1chen.scudsbook_deliver_client;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by ye1.chen on 7/20/16.
 */
public class ScudsbookUtil {
    public static void setUpListItemCurrentOrderView(Context context, View v, OrderInfo info) {
        TextView deliverBy = (TextView) v.findViewById(R.id.tv_order_op);
        TextView orderId = (TextView) v.findViewById(R.id.tv_order_id);
        TextView orderTime = (TextView) v.findViewById(R.id.tv_order_time);
        TextView customerName = (TextView) v.findViewById(R.id.tv_customer_name);
        TextView phoneNum = (TextView) v.findViewById(R.id.tv_order_phone);
        TextView distance = (TextView) v.findViewById(R.id.tv_distance);
        TextView address = (TextView) v.findViewById(R.id.tv_address);
        TextView orderTotal = (TextView) v.findViewById(R.id.tv_order_total);

        if(info != null) {
            if(!TextUtils.isEmpty(info.getDeliverBy())) {
                String temp = context.getResources().getString(R.string.text_deliver_by) + info.getDeliverBy();
                deliverBy.setText(temp);
            }
            orderId.setText(info.getId());
            orderTime.setText(getDateFromLong(info.getOrderTime()));
            customerName.setText(info.getCustomerName());
            phoneNum.setText(info.getCustomerPhone());
            distance.setText(info.getDistance());
            address.setText(mergeAddress(info.getAddress(), info.getCity(), info.getState(), info.getZip()));
            orderTotal.setText(mergeTotal(context, info.getProductCost(), info.getDeliverFee(), info.getTip(), info.getTotal()));
        }
    }

    /**
     * Change mill seconds to Data
     * @param milliseconds
     * @return
     */
    public static String getDateFromLong(String milliseconds){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(Long.parseLong(milliseconds));
        return sdf.format(resultdate);
    }

    public static String mergeAddress(String address, String city, String state, String zip) {
        return (address + "," + city + "," + state + "," +zip);
    }

    public static String mergeTotal(Context context, String product, String deliver, String tip, String total) {
        String temp = "$" + product + "(" + context.getResources().getString(R.string.products) + ')' + " + "
                + "$" + deliver + "(" + context.getResources().getString(R.string.deliver) + ')' + " + "
                + "$" + tip + "(" + context.getResources().getString(R.string.tip) + ')' + " = "
                + "$" + total;
        return temp;
    }
}
