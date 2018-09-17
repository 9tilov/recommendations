package com.sadi.toor.recommend.filter.year.viewmodel;

import com.sadi.toor.recommend.core.base.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class YearViewModel extends BaseViewModel {

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