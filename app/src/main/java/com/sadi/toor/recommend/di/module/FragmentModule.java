package com.sadi.toor.recommend.di.module;

import com.sadi.toor.recommend.filter.genre.ui.GenreFragment;
import com.sadi.toor.recommend.filter.ui.FilterFragment;
import com.sadi.toor.recommend.filter.year.ui.YearFragment;
import com.sadi.toor.recommend.preparing.ui.MainFragment;
import com.sadi.toor.recommend.recommendation.ui.RecommendFragment;

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
    abstract RecommendFragment contributeRecommendFragment();

    @ContributesAndroidInjector
    abstract FilterFragment contributeFilterFragment();

    @ContributesAndroidInjector
    abstract GenreFragment contributeGenreFragment();

    @ContributesAndroidInjector
    abstract YearFragment contributeYearFragment();

}
