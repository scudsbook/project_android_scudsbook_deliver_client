
package com.example.ye1chen.scudsbook_deliver_client.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;

import java.util.ArrayList;
import java.util.List;

public class ScudsbookDba {

    private static final String TAG = "ScudsbookDba";

    private static ScudsbookDba mDba;

    private static String PROFILE_SELECTION_BY_ID = ScudsbookProvider.OrderInfoColumns._ID + " LIKE ? ";

    public static String ID_SELECTION = BaseColumns._ID + "=?";

    public static ScudsbookDba getDB() {
        if (mDba == null)
            mDba = new ScudsbookDba();
        return mDba;
    }

    /**
     * Save order
     *
     * @param cr
     * @param info
     * @return
     */
    public Uri saveOrderInfo(ContentResolver cr, OrderInfo info) {
        if (info == null) {
            return null;
        }

        ContentValues values = new ContentValues();
        ScudsbookDbUtil.toContentValues(info, values);

        Log.d(TAG, "insert order " + info.getCustomerName());
        return cr.insert(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, values);
    }

    public List<OrderInfo> getAllOrder(ContentResolver cr) {
        List<OrderInfo> orderList = null;
        OrderInfo info = null;
        Cursor cursor = null;

        try {
            cursor = cr.query(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                orderList = new ArrayList<>();
                do {
                    info = new OrderInfo();
                    ScudsbookDbUtil.fromCursor(cursor, info);
                    orderList.add(info);
                } while (cursor.moveToNext());

            }
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return orderList;
    }


    public OrderInfo getOrderInfoById(ContentResolver cr, String id) {
        OrderInfo info = null;
        Log.d(TAG, "{getAAProfile} the ID is : " + id);
        if (id == null)
            return null;

        Cursor cursor = null;

        try {
            cursor = cr.query(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, null, PROFILE_SELECTION_BY_ID,
                    new String[]{
                            id
                    }, null);
            if (cursor != null && cursor.moveToFirst()) {
                info = new OrderInfo();
                ScudsbookDbUtil.fromCursor(cursor, info);

            }
        } catch (SQLException e) {
            Log.e(TAG, "Error in retrieve Date: " + id + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return info;
    }

    public int deleteAAProfile(ContentResolver cr, OrderInfo profile) {
        int count = 0;
        if (profile != null) {
            count = cr.delete(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, ID_SELECTION, new String[]{
                    profile.getId()
            });
        }
        return count;
    }

    public int updateAAProfile(ContentResolver cr, OrderInfo profile) {
        if (profile == null) {
            return 0;
        }
        ContentValues contentValues = new ContentValues();
        ScudsbookDbUtil.toContentValues(profile, contentValues);
        return cr.update(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, contentValues, ID_SELECTION, new String[] {
                profile.getId()
        });
    }
}
