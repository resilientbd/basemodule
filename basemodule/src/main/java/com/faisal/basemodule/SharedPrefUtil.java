package com.faisal.basemodule;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefUtil {

    private static final String SHARED_PREF = "module_preference";

    public static void ADD_PREFERENCE(Context context, String name, String value, boolean isString) {

        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (isString) {
            editor.putString(name, value);
        } else {
            editor.putInt(name, Integer.parseInt(value));
        }

        editor.apply();
    }

    public static void ADD_BOOLEAN_PREFERENCE(Context context, String name, boolean value) {

        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public static void CLEAR_PREFERENCE(Context context) {

        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public static String GET_STRING_PREFERENCE(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return pref.getString(name, "");
    }

    public static int GET_INT_PREFERENCE(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return pref.getInt(name, 0);

    }

    public static boolean GET_BOLEAN_PREFERENCE(Context context, String name, boolean defaultVal) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return pref.getBoolean(name, defaultVal);

    }
    public static void ADD_FLOAT_PREFERENCE(Context context, String name, float value) {

        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(name, value);
        editor.apply();
    }
    public static float GET_FLOAT_PREFERENCE(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return pref.getFloat(name, 0.0f);

    }

}
