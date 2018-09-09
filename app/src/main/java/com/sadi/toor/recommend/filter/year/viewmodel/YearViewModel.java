package com.sadi.toor.recommend.filter.year.viewmodel;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class YearViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    YearViewModel() {
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}