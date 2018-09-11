package com.sadi.toor.recommend.core.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sadi.toor.recommend.model.network.NetworkRetryManager;
import com.sadi.toor.recommend.model.network.RetryManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    protected final RetryManager retryManager = new NetworkRetryManager();
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected final MutableLiveData<Status> status = new MutableLiveData<>();
    private List<MutableLiveData> observers = new ArrayList<>();

    public BaseViewModel() {
        addObserver(status);
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public <T> void addObserver(MutableLiveData<T> observer) {
        observers.add(observer);
    }

    public void unsubscribeFromDestroy(LifecycleOwner owner) {
        for (MutableLiveData observer : observers) {
            observer.removeObservers(owner);
        }
    }

    public void retryCall() {
        retryManager.retry();
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
