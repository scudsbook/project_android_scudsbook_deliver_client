package com.example.ye1chen.scudsbook_deliver_client;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by ye1.chen on 5/18/16.
 */
public class UserInfo {
    private String userName;
    private boolean isManager;
    private static UserInfo mInfo;
    private Context mContext;

    private UserInfo(Context context) {
        mContext = context;
        this.isManager = false;
    }

    public static UserInfo getInstance(Context context) {
        if(mInfo == null) {
            mInfo = new UserInfo(context);
        }
        return mInfo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setManagerCheck(boolean check) {
        isManager = check;
    }

    public boolean getManagerCheck() {
        return isManager;
    }

    public String getUserName() {
        return userName;
    }
}
