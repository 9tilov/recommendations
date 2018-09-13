package com.sadi.toor.recommend.recommendation.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.base.Status;
import com.sadi.toor.recommend.interactor.MovieInteractor;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecommendViewModel extends BaseViewModel {

    private final MovieInteractor movieInteractor;
    private final MutableLiveData<Recommendations> recommendations = new MutableLiveData<>();

    @Inject
    RecommendViewModel(MovieInteractor movieInteractor) {
        this.movieInteractor = movieInteractor;
        addObserver(recommendations);
        loadRecommendations();
    }

    private void loadRecommendations() {
        compositeDisposable.add(movieInteractor.getRecommendations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> status.postValue(Status.START_LOADING))
                .doOnError(throwable -> {
                    status.postValue(Status.ERROR);
                })
                .retryWhen(retryHandler -> retryHandler.flatMap(retryManager::observeRetries))
                .subscribe(value -> {
                    status.postValue(Status.SUCCESS);
                    recommendations.postValue(value);
                }));
    }

    public MutableLiveData<Recommendations> getRecommendations(){
        return recommendations;
    }
}
