package com.sadi.toor.recommend.model.data.preferences;

import android.content.SharedPreferences;

public class PreferenceRepo {

    private static final String PREF_ACCESS_TOKEN = "access_token";

    private SharedPreferences sharedPreferences;

    public PreferenceRepo(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveUserToken(String token) {
        sharedPreferences.edit().putString(PREF_ACCESS_TOKEN, token).apply();
    }

    public String getUserToken() {
        return sharedPreferences.getString(PREF_ACCESS_TOKEN, "");
    }
}
