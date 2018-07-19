package com.sadi.toor.recommend.viewmodel;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class FavoriteMovieViewModel extends ViewModel {

    @Inject
    FavoriteMovieViewModel() {

    }

    @Override
    public void onCleared() {
        super.onCleared();
    }
}
