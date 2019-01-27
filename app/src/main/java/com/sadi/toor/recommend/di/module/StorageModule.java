package com.sadi.toor.recommend.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.sadi.toor.recommend.model.data.preferences.PreferenceRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("storage", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    PreferenceRepo providePreferences(SharedPreferences sharedPreferences) {
        return new PreferenceRepo(sharedPreferences);
    }

}
