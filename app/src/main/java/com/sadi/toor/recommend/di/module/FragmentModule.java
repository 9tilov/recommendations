package com.sadi.toor.recommend.di.module;

import com.sadi.toor.recommend.view.ui.GenreFragment;
import com.sadi.toor.recommend.view.ui.FavoriteMovieFragment;
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
    abstract FavoriteMovieFragment contributeRateSwipeFragment();

    @ContributesAndroidInjector
    abstract GenreFragment contributeGenreFragment();

}
