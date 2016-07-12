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
    }

    public static void fromCursor(Cursor cursor, OrderInfo profile) {
        int idxId = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns._ID);
        int idxOrderId = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.ORDER_ID);
        int idxDate = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.ORDER_DATE);
        int idxStore = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.ORDER_STORE);
        int idxDetail = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.ORDER_DETAIL);
        int idxCashbackCompany = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.ORDER_CASHBACK_COMPANY);
        int idxCashbackState = cursor.getColumnIndexOrThrow(ScudsbookProvider.OrderInfoColumns.ORDER_CASHBACK_STATE);
        int idxCashbackPercent = cursor.getColumnIndexOrThrow(AAProvider.ProfileColumns.ORDER_CASHBACK_PERCENT);
        int idxCashbackAmount = cursor.getColumnIndexOrThrow(AAProvider.ProfileColumns.ORDER_CASHBACK_AMOUNT);
        int idxCategory = cursor.getColumnIndexOrThrow(AAProvider.ProfileColumns.ORDER_CATEGORY);
        int idxCost = cursor.getColumnIndexOrThrow(AAProvider.ProfileColumns.ORDER_TOTAL_COST);
        int idxPartCost = cursor.getColumnIndexOrThrow(AAProvider.ProfileColumns.ORDER_PRICE_CB_AVAILABLE);
        int idxPaymentFrom = cursor.getColumnIndexOrThrow(AAProvider.ProfileColumns.ORDER_PAYMENT_FROM);

        profile.setId(cursor.getString(idxId));
        profile.setOrderId(cursor.getString(idxOrderId));
        profile.setDate(cursor.getString(idxDate));
        profile.setOrderStore(cursor.getString(idxStore));
        profile.setOrderDetail(cursor.getString(idxDetail));
        profile.setCashbackCompany(cursor.getString(idxCashbackCompany));
        profile.setCashbackState(cursor.getString(idxCashbackState));
        profile.setCashbackPercent(cursor.getString(idxCashbackPercent));
        profile.setCashbackAmount(cursor.getString(idxCashbackAmount));
        profile.setCat(cursor.getString(idxCategory));
        profile.setOrderCost(cursor.getString(idxCost));
        profile.setAvailableCost(cursor.getString(idxPartCost));
        profile.setPaymentFrom(cursor.getString(idxPaymentFrom));
    }
}
