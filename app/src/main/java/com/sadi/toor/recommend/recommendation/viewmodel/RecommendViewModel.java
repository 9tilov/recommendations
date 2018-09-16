package com.sadi.toor.recommend.recommendation.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.base.Status;
import com.sadi.toor.recommend.filter.interactor.FilterInteractor;
import com.sadi.toor.recommend.model.data.movie.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecommendViewModel extends BaseViewModel {

    private final FilterInteractor filterInteractor;
    private final MutableLiveData<List<Movie>> recommendationData = new MutableLiveData<>();
    private List<Movie> recommendations = new ArrayList<>();

    @Inject
    RecommendViewModel(FilterInteractor filterInteractor) {
        this.filterInteractor = filterInteractor;
        addObserver(recommendationData);
        loadRecommendations();
    }

    private void loadRecommendations() {
        compositeDisposable.add(filterInteractor.getRecommendations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> status.postValue(Status.START_LOADING))
                .doOnError(throwable -> {
                    status.postValue(Status.ERROR);
                })
                .retryWhen(retryHandler -> retryHandler.flatMap(retryManager::observeRetries))
                .subscribe(value -> {
                    status.postValue(Status.SUCCESS);
                    recommendations = value;
                    recommendationData.postValue(value);
                }));
    }

    public void getFilteredRecommendations() {
        Observable.fromIterable(recommendations)
                .filter(filterInteractor::isInYearPeriod)
                .filter(filterInteractor::hasGenres)
                .toList()
                .toObservable()
                .subscribe(movies -> {
                    recommendationData.postValue(movies);
                });
    }

    public MutableLiveData<List<Movie>> getRecommendationData() {
        return recommendationData;
    }
}
