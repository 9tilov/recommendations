package com.sadi.toor.recommend.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.sadi.toor.recommend.auth.AuthViewModel;
import com.sadi.toor.recommend.core.base.FactoryViewModel;
import com.sadi.toor.recommend.di.scope.ViewModelKey;
import com.sadi.toor.recommend.filter.genre.viewmodel.GenreViewModel;
import com.sadi.toor.recommend.filter.viewmodel.FilterViewModel;
import com.sadi.toor.recommend.filter.year.viewmodel.YearViewModel;
import com.sadi.toor.recommend.preparing.viewmodel.MainViewModel;
import com.sadi.toor.recommend.recommendation.viewmodel.RecommendViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GenreViewModel.class)
    abstract ViewModel bindGenreViewModel(GenreViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RecommendViewModel.class)
    abstract ViewModel bindRecommendViewModel(RecommendViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FilterViewModel.class)
    abstract ViewModel bindFilterViewModel(FilterViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(YearViewModel.class)
    abstract ViewModel bindYearViewModel(YearViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
