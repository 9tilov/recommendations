package com.sadi.toor.recommend.di.component;

import com.sadi.toor.recommend.App;
import com.sadi.toor.recommend.di.module.ActivityModule;
import com.sadi.toor.recommend.di.module.AppModule;
import com.sadi.toor.recommend.di.module.FragmentModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AppModule.class, ActivityModule.class, FragmentModule.class})
interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }
}