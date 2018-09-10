package com.sadi.toor.recommend.core.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<MutableLiveData> observers = new ArrayList<>();

    public <T> void addObserver(MutableLiveData<T> observer) {
        observers.add(observer);
    }

    public void unsubscribeFromDestroy(LifecycleOwner owner) {
        for (MutableLiveData observer : observers) {
            observer.removeObservers(owner);
        }
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
