
package com.example.ye1chen.scudsbook_deliver_client.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.acme.international.trading.cashbacktracker.CashbackProfile;
import com.acme.international.trading.cashbacktracker.CbUtils;

import java.util.ArrayList;
import java.util.List;

public class ScudsbookDba {

    private static final String TAG = "ScudsbookDba";

    private static ScudsbookDba mDba;

    // Query string constants to work with database.
    private static String PROFILE_SELECTION_BY_DATE = ScudsbookProvider.OrderInfoColumns.ORDER_DATE + " LIKE ? ";

    public static String PROFILE_SELECTION_BY_CASHBACK_STATE = ScudsbookProvider.OrderInfoColumns.ORDER_CASHBACK_STATE + " LIKE ? ";

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
     * @param profile
     * @return
     */
    public Uri saveCbProfile(ContentResolver cr, ScudsbookDba profile) {
        if (profile == null) {
            return null;
        }

        ContentValues values = new ContentValues();
        CbUtils.toContentValues(profile, values);

        Log.d(TAG, "insert order " + profile.getDate());
        return cr.insert(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, values);
    }

    public List<CashbackProfile> getAllProfile(ContentResolver cr) {
        List<CashbackProfile> profileList = null;
        CashbackProfile profile = null;
        Cursor cursor = null;

        try {
            cursor = cr.query(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                profileList = new ArrayList<CashbackProfile>();
                do {
                    profile = new CashbackProfile();
                    CbUtils.fromCursor(cursor, profile);
                    profileList.add(profile);
                } while (cursor.moveToNext());

            }
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return profileList;
    }

    public CashbackProfile getAAProfileById(ContentResolver cr, String id) {
        CashbackProfile profile = null;
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
                profile = new CashbackProfile();
                CbUtils.fromCursor(cursor, profile);

            }
        } catch (SQLException e) {
            Log.e(TAG, "Error in retrieve Date: " + id + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return profile;
    }

    public List<CashbackProfile> getCbProfileBySelection(ContentResolver cr, String selection, String key) {
        List<CashbackProfile> profileList = null;
        Log.d(TAG, "{getAAProfile} the KEY is : " + key);
        if (key == null)
            return null;

        CashbackProfile profile = null;
        Cursor cursor = null;

        try {
            cursor = cr.query(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, null, selection,
                    new String[]{
                            key
                    }, null);
            if (cursor != null && cursor.moveToFirst()) {
                profileList = new ArrayList<CashbackProfile>();
                do {
                    profile = new CashbackProfile();
                    CbUtils.fromCursor(cursor, profile);
                    profileList.add(profile);
                } while (cursor.moveToNext());

            }
        } catch (SQLException e) {
            Log.d(TAG, "Error in retrieve Key: " + key, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return profileList;
    }

    public int deleteAAProfile(ContentResolver cr, CashbackProfile profile) {
        int count = 0;
        if (profile != null) {
            count = cr.delete(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, ID_SELECTION, new String[]{
                    profile.getId()
            });
        }
        return count;
    }

    public int deleteAAProfile(ContentResolver cr, String id) {
        int count = 0;
        CashbackProfile profile = getAAProfileById(cr, id);
        if (profile != null) {
            count = cr.delete(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, ID_SELECTION, new String[]{
                    profile.getId()
            });
        }
        return count;
    }

    public int updateAAProfile(ContentResolver cr, CashbackProfile profile) {
        if (profile == null) {
            return 0;
        }
        ContentValues contentValues = new ContentValues();
        CbUtils.toContentValues(profile, contentValues);
        return cr.update(ScudsbookProvider.OrderInfoColumns.CONTENT_URI, contentValues, ID_SELECTION, new String[] {
                profile.getId()
        });
    }
}
