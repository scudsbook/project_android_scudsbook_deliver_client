package com.example.ye1chen.scudsbook_deliver_client.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;
import com.example.ye1chen.scudsbook_deliver_client.database.ScudsbookProvider.OrderInfoColumns;

/**
 * Created by ye1.chen on 7/12/16.
 */
public class ScudsbookDbUtil {

    public static void toContentValues(OrderInfo orderInfo, ContentValues values) {
        values.put(OrderInfoColumns.CUSTOMER_NAME, orderInfo.getCustomerName());
        values.put(OrderInfoColumns.CUSTOMER_PHONE, orderInfo.getCustomerPhone());
        values.put(OrderInfoColumns.DISTANCE, orderInfo.getDistance());
        values.put(OrderInfoColumns.ADDRESS, orderInfo.getAddress());
        values.put(OrderInfoColumns.CITY, orderInfo.getCity());
        values.put(OrderInfoColumns.STATE, orderInfo.getState());
        values.put(OrderInfoColumns.ZIP, orderInfo.getZip());
        values.put(OrderInfoColumns.PRODUCT_COST, orderInfo.getProductCost());
        values.put(OrderInfoColumns.DELIVERED_FEE, orderInfo.getDeliverFee());
        values.put(OrderInfoColumns.TIP, orderInfo.getTip());
        values.put(OrderInfoColumns.TOTAL, orderInfo.getTotal());
        values.put(OrderInfoColumns.DELIVER_BY, orderInfo.getDeliverBy());
        values.put(OrderInfoColumns.ORDER_TIME, orderInfo.getOrderTime());
        values.put(OrderInfoColumns.SUMMARY, orderInfo.getOrderSum());
    }

    public static void fromCursor(Cursor cursor, OrderInfo info) {
        int idxId = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns._ID);
        int idxCustomerName = cursor.getColumnIndexOrThrow(OrderInfoColumns.CUSTOMER_NAME);
        int idxCustomerPhone = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.CUSTOMER_PHONE);
        int idxDistance = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.DISTANCE);
        int idxAddress = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.ADDRESS);
        int idxCity = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.CITY);
        int idxState = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.STATE);
        int idxZip = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.ZIP);
        int idxProductCost = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.PRODUCT_COST);
        int idxDeliveredFee = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.DELIVERED_FEE);
        int idxTip = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.TIP);
        int idxTotal = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.TOTAL);
        int idxDeliveredBy = cursor.getColumnIndexOrThrow(OrderInfoColumns.DELIVER_BY);
        int idxOrderTime = cursor.getColumnIndexOrThrow(OrderInfoColumns.ORDER_TIME);
        int idxSum = cursor.getColumnIndexOrThrow(OrderInfoColumns.SUMMARY);

        info.setId(cursor.getString(idxId));
        info.setCustomerName(cursor.getString(idxCustomerName));
        info.setCustomerPhone(cursor.getString(idxCustomerPhone));
        info.setDistance(cursor.getString(idxDistance));
        info.setAddress(cursor.getString(idxAddress));
        info.setCity(cursor.getString(idxCity));
        info.setState(cursor.getString(idxState));
        info.setZip(cursor.getString(idxZip));
        info.setProductCost(cursor.getString(idxProductCost));
        info.setDeliverFee(cursor.getString(idxDeliveredFee));
        info.setTip(cursor.getString(idxTip));
        info.setTotal(cursor.getString(idxTotal));
        info.setDeliverBy(cursor.getString(idxDeliveredBy));
        info.setOrderTime(cursor.getString(idxOrderTime));
        info.setOrderSum(cursor.getString(idxSum));
    }
}
