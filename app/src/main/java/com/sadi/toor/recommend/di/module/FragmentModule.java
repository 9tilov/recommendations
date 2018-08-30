package com.sadi.toor.recommend.di.module;

import com.sadi.toor.recommend.view.ui.RecommendFragment;
import com.sadi.toor.recommend.view.ui.GenreFragment;
import com.sadi.toor.recommend.view.ui.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Philippe on 02/03/2018.
 */

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();

    @ContributesAndroidInjector
    abstract GenreFragment contributeGenreFragment();

    @ContributesAndroidInjector
    abstract RecommendFragment contributeRecommendFragment();

}
