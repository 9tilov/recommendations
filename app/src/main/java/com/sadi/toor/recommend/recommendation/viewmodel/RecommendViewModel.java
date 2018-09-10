package com.sadi.toor.recommend.recommendation.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.wrapper.DataWrapper;
import com.sadi.toor.recommend.core.wrapper.ErrorObject;
import com.sadi.toor.recommend.model.data.Wish;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;
import com.sadi.toor.recommend.model.repo.DataRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RecommendViewModel extends BaseViewModel {

    private final DataRepository repository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<DataWrapper<Recommendations>> recommendations = new MutableLiveData<>();

    @Inject
    RecommendViewModel(DataRepository repository) {
        this.repository = repository;
        addObserver(recommendations);
    }

    public void sendUserMovies(Wish wish) {
        compositeDisposable.add(repository.sendUserWish(wish)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    recommendations.postValue(new DataWrapper<>(value, null));
                }, t -> {
                    recommendations.postValue(new DataWrapper<>(null, new ErrorObject(t.getMessage())));
                    Timber.d("moggot = " + t.getMessage());
                }));
    }

    public MutableLiveData<DataWrapper<Recommendations>> getRecommendations() {
        return recommendations;
    }
}
