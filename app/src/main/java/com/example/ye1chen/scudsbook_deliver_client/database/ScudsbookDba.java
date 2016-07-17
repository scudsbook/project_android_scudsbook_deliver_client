
package com.example.ye1chen.scudsbook_deliver_client.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

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

}
