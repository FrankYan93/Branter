package com.branter.jiadongyan.branter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jerry on 11/27/17.
 */

public class SaveSharedPreference {
    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_ID = "1";
    static final String PREF_EVENT_ID = "1";
    static CSC data_client = new CSC();

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static void setUserID(Context ctx, String id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ID, id);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getUserID(Context ctx)

    {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }

    public static void setEventID(Context ctx, String id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_EVENT_ID, id);
        editor.commit();
    }

    public static String getEventID(Context ctx)

    {
        return getSharedPreferences(ctx).getString(PREF_EVENT_ID, "");
    }
}
