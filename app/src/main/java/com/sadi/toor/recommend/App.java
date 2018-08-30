package com.sadi.toor.recommend;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.stetho.Stetho;
import com.sadi.toor.recommend.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initDagger();
        Timber.plant(new Timber.DebugTree());
        Stetho.initializeWithDefaults(this);
        configureCrashReporting();
    }

    private void configureCrashReporting() {
        CrashlyticsCore crashlyticsCore =
                new CrashlyticsCore.Builder()
                        .disabled(BuildConfig.DEBUG)
                        .build();
        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (BuildConfig.DEBUG) {
            MultiDex.install(this);
        }
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    private void initDagger() {
        DaggerAppComponent.builder().create(this).inject(this);
    }
}
