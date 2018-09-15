package com.sadi.toor.recommend;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {

    private static final String FIREBASE_ITEM_ID = "id";
    private static final String FIREBASE_ITEM_NAME = "name";
    private static final String FIREBASE_CONTENT_TYPE = "image";

    private FirebaseAnalytics firebaseAnalytics;

    public Analytics(Context ctx) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(ctx);
    }

    public void init() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, FIREBASE_ITEM_ID);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, FIREBASE_ITEM_NAME);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, FIREBASE_CONTENT_TYPE);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void setCurrentScreen(Activity activity, String tag) {
        firebaseAnalytics.setCurrentScreen(activity, tag, null);
    }
}
