package com.sadi.toor.recommend.di.module;

import android.content.Context;

import com.sadi.toor.recommend.App;
import com.sadi.toor.recommend.core.scheduler.AppSchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ViewModelModule.class, NetworkModule.class, StorageModule.class})
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(App application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    AppSchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}