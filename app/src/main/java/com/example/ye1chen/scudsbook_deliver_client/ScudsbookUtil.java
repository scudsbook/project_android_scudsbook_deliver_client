package com.example.ye1chen.scudsbook_deliver_client;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

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

    public static HashMap<String,String> transferInfoToMap(Context context, HashMap<String,String> hashMap, OrderInfo info) {
        hashMap.put(ScudsbookConstants.key_scudsbook, context.getResources().getString(R.string.key_connection));
        hashMap.put(ScudsbookConstants.key_type, ScudsbookConstants.type_order_info_update);
        hashMap.put(ScudsbookConstants.user_name, UserInfo.getInstance(context).getUserName());

        hashMap.put(ScudsbookConstants.key_order_info_id, info.getId());
        hashMap.put(ScudsbookConstants.key_order_info_customer_name, info.getCustomerName());
        hashMap.put(ScudsbookConstants.key_order_info_customer_phone, info.getCustomerPhone());
        hashMap.put(ScudsbookConstants.key_order_info_distance, info.getDistance());
        hashMap.put(ScudsbookConstants.key_order_info_address, info.getAddress());
        hashMap.put(ScudsbookConstants.key_order_info_city, info.getCity());
        hashMap.put(ScudsbookConstants.key_order_info_state, info.getState());
        hashMap.put(ScudsbookConstants.key_order_info_zip, info.getZip());
        hashMap.put(ScudsbookConstants.key_order_info_product_cost, info.getProductCost());
        hashMap.put(ScudsbookConstants.key_order_info_deliver_fee, info.getDeliverFee());
        hashMap.put(ScudsbookConstants.key_order_info_tip, info.getTip());
        hashMap.put(ScudsbookConstants.key_order_info_total, info.getTotal());
        hashMap.put(ScudsbookConstants.key_order_info_deliver_by, info.getDeliverBy());
        hashMap.put(ScudsbookConstants.key_order_info_order_summary, info.getOrderSum());
        hashMap.put(ScudsbookConstants.key_order_info_order_time, info.getOrderTime());

        return hashMap;
    }

    public static ArrayList<OrderInfo> getOrderInfoListFromServer(Context context) {
        ArrayList<OrderInfo> list = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(ScudsbookConstants.key_scudsbook, context.getResources().getString(R.string.key_connection));
        hashMap.put(ScudsbookConstants.key_type, ScudsbookConstants.type_manager_order_info_list_query);
        hashMap.put(ScudsbookConstants.user_name, UserInfo.getInstance(context).getUserName());
        String respond = HttpConnection.postRequest(hashMap,5000,5000);
        if (TextUtils.isEmpty(respond))
            return list;

        String[] orderInfoList = respond.split("[}]");
        for(int i = 0; i<orderInfoList.length; i++) {
            if(TextUtils.isEmpty(orderInfoList[i]))
                continue;
            String[] item = orderInfoList[i].split(";");
            OrderInfo info = new OrderInfo();
            info.setId(item[0]);
            info.setCustomerName(item[1]);
            info.setCustomerPhone(item[2]);
            info.setDistance(item[3]);
            info.setAddress(item[4]);
            info.setCity(item[5]);
            info.setState(item[6]);
            info.setZip(item[7]);
            info.setProductCost(item[8]);
            info.setDeliverFee(item[9]);
            info.setTip(item[10]);
            info.setTotal(item[11]);
            info.setDeliverBy(item[12]);
            info.setOrderSum(item[13]);
            info.setOrderTime(item[14]);
            list.add(info);
        }
        return list;
    }
}
