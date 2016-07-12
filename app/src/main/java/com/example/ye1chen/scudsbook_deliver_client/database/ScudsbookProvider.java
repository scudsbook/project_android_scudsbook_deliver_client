
package com.example.ye1chen.scudsbook_deliver_client.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

public class ScudsbookProvider extends ContentProvider {

    public static final String AUTHORITY = "com.scudsbook.provider";

    public static final String DB_NAME = "scudsbook.db";

    private static final int DB_VERSION = 1;

    private static final String TAG = "ScudsbookProvider";

    // URL matcher path constants
    // Product order from merchant
    private static final int OrderInfo = 1;

    private static final int OrderInfo_ID = 2;

    private AADatabaseHelper mHelper;

    // Amazon Order profile
    public interface OrderInfoColumns extends BaseColumns {
        String TBL_SS_ORDER_INFO = "order_info";

        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
                + TBL_SS_ORDER_INFO);

        String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.scudsbook.orderinfo";

        String CUSTOMER_NAME = "customer_name";
        String CUSTOMER_PHONE = "customer_phone";
        String DISTANCE = "distance";
        String ADDRESS = "address";
        String CITY = "city";
        String ZIP = "zip";
        String STATE = "state";
        String PRODUCT_COST = "product_cost";
        String DELIVERED_FEE = "delivered_fee";
        String TIP = "tip";
        String TOTAL = "total";
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String whichTable = getTable(uri);
        int count = db.delete(whichTable, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null, false);
        return count;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        String type = null;
        switch (urlMatcher.match(uri)) {
            case OrderInfo:
                type = OrderInfoColumns.CONTENT_TYPE;
                break;
        }
        return type;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String whichTable = getTable(uri);
        if (values == null) {
            values = new ContentValues();
        }

        long rowId = db.insert(whichTable, BaseColumns._ID, values);

        if (rowId >= 0) {
            Uri url = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(url, null, false);
            return url;
        }

        throw new SQLiteException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        mHelper = new AADatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sort) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String sortOrder = null;
        int match = urlMatcher.match(uri);
        switch (match) {
            case OrderInfo:
                qb.setTables(OrderInfoColumns.TBL_SS_ORDER_INFO);
                sortOrder = (sort != null ? sort : OrderInfoColumns._ID);
                break;
            case OrderInfo_ID:
                qb.setTables(OrderInfoColumns.TBL_SS_ORDER_INFO);
                qb.appendWhere("_id=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }

        Log.d(TAG,
                "Build query: " + qb.buildQuery(projection, selection, null, null, sortOrder, null));

        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String whichTable = getTable(uri);

        int count;
        int match = urlMatcher.match(uri);
        switch (match) {
            case OrderInfo:
                count = db.update(whichTable, values, selection, selectionArgs);
                break;
            case OrderInfo_ID:
                String segment = uri.getPathSegments().get(1);
                count = db
                        .update(whichTable, values,
                                "_id="
                                        + segment
                                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                                                + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }

        if (count > 0)
            getContext().getContentResolver().notifyChange(uri, null, false);

        return count;
    }

    public static class AADatabaseHelper extends SQLiteOpenHelper {

        // private Context ctx;
        public AADatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            // this.ctx = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Profi.les
            db.execSQL("CREATE TABLE " + OrderInfoColumns.TBL_SS_ORDER_INFO + " (" + OrderInfoColumns._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + OrderInfoColumns.CUSTOMER_NAME
                    + " VARCHAR, " + OrderInfoColumns.CUSTOMER_PHONE + " VARCHAR, "
                    + OrderInfoColumns.DISTANCE + " VARCHAR, " + OrderInfoColumns.ADDRESS
                    + " VARCHAR, " + OrderInfoColumns.CITY + " VARCHAR, "
                    + OrderInfoColumns.ZIP + " VARCHAR, "
                    + OrderInfoColumns.PRODUCT_COST + " VARCHAR, "
                    + OrderInfoColumns.DELIVERED_FEE + " VARCHAR, "
                    + OrderInfoColumns.TIP + " VARCHAR, "
                    + OrderInfoColumns.TOTAL + " VARCHAR);");
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "Downgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            Log.i(TAG, "Database has been opened for operations.");
        }
    }

    private static final UriMatcher urlMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        urlMatcher.addURI(AUTHORITY, OrderInfoColumns.TBL_SS_ORDER_INFO, OrderInfo);
        urlMatcher.addURI(AUTHORITY, OrderInfoColumns.TBL_SS_ORDER_INFO + "/#", OrderInfo_ID);
    }

    /**
     * Get the targeting table based on given url match.
     * 
     * @param uri uri for the table
     * @return table
     */
    private String getTable(Uri uri) {
        String whichTable;
        int match = urlMatcher.match(uri);
        switch (match) {
            case OrderInfo:
            case OrderInfo_ID:
                whichTable = OrderInfoColumns.TBL_SS_ORDER_INFO;
                break;
            default:
                throw new IllegalArgumentException("Unknown URL: " + uri);
        }

        Log.d(TAG, "Selected table is " + whichTable);
        return whichTable;
    }
}
