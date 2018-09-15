package com.sadi.toor.recommend.recommendation.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.base.Status;
import com.sadi.toor.recommend.filter.interactor.FilterInteractor;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RecommendViewModel extends BaseViewModel {

    private final FilterInteractor filterInteractor;
    private final MutableLiveData<Recommendations> recommendationData = new MutableLiveData<>();
    private Recommendations recommendations = new Recommendations(new ArrayList<>());

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
        Observable.just(recommendations)
                .flatMap(recommendations1 -> Observable.fromIterable(recommendations1.getMovies()))
                .filter(new Predicate<Movie>() {
                    @Override
                    public boolean test(Movie movie) throws Exception {
                        Timber.d("moggot year = " + filterInteractor.isInYearPeriod(movie));
                        return filterInteractor.isInYearPeriod(movie);
                    }
                })
                .filter(new Predicate<Movie>() {
                    @Override
                    public boolean test(Movie movie) throws Exception {
                        Timber.d("moggot genre = " + filterInteractor.hasGenres(movie));
                        return filterInteractor.hasGenres(movie);
                    }
                })
                .toList()
                .toObservable()
                .subscribe(movies -> {
                    recommendationData.postValue(new Recommendations(movies));
                });
    }

    public MutableLiveData<Recommendations> getRecommendationData() {
        return recommendationData;
    }
}
