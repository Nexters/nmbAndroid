package com.nexters.naemambo.naemambo.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016-04-20.
 */
public class SPreference {
    private final String PREF_NAME = "NaeMambo";

    static Context mContext;
    private String TAG = "SPreference";
    SharedPreferences pref;

    public SPreference(Context c) {
        mContext = c;
    }

    public void remove(String key) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.remove(key);
        editor.commit();
    }

    /**
     * sharedpreference 에 키가있으면 true 아니면 false
     *
     * @param key
     * @return
     */
    public boolean has(String key) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        return pref.getAll().containsKey(key);
    }

    public void put(String key, String value) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void put(String key, Set<String> value) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    public void put(String key, float value) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
//        Log.e(TAG, "put float key : " + key + " / put float value : " + value);
        editor.putFloat(key, value);
        editor.commit();
    }

    public void put(String key, boolean value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    public void put(String key, int dftValue) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
//        Log.e(TAG, "put int key : " + key + " / put int value : " + pref.getInt(key, dftValue));
        editor.putInt(key, dftValue);
        editor.commit();
    }

    public float getFloat(String key, float dftValue) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);

//        Log.e(TAG, "get float key : " + key + " / get float value : " + pref.getFloat(key, dftValue));
        return pref.getFloat(key, dftValue);
    }

    public Set<String> getHashSet(String key, HashSet<String> dftValue) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);

//        Log.e(TAG, "get float key : " + key + " / get float value : " + pref.getFloat(key, dftValue));
        return pref.getStringSet(key, dftValue);
    }

    public String getString(String key, String dftValue) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);

        return pref.getString(key, dftValue);
    }

    public int getInt(String key, int dftValue) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        return pref.getInt(key, dftValue);
    }

    public boolean getBool(String key, boolean dftValue) {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);

        try {
            return pref.getBoolean(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public String getAllValue() {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        try {
            return pref.getAll().toString();
        } catch (Exception e) {
            return "no Preference Data";
        }
    }

    /**
     * remove all key, value
     */
    public void clear() {
        pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
