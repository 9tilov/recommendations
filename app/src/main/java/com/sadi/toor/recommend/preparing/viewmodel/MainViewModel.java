package com.sadi.toor.recommend.preparing.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.base.Status;
import com.sadi.toor.recommend.interactor.MovieInteractor;
import com.sadi.toor.recommend.interactor.MovieProgressStatus;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.movie.Movies;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

    private final MutableLiveData<Movies> moviesList = new MutableLiveData<>();
    private final MovieInteractor movieInteractor;

    @Inject
    MainViewModel(MovieInteractor movieInteractor) {
        this.movieInteractor = movieInteractor;
        addObserver(moviesList);
        loadMovies();
    }

    private void loadMovies() {
        compositeDisposable.add(movieInteractor.getPrepareMovieList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    status.postValue(Status.START_LOADING);
                })
                .doOnError(throwable -> {
                    status.postValue(Status.ERROR);
                })
                .retryWhen(retryHandler -> retryHandler.flatMap(retryManager::observeRetries))
                .subscribe(movies -> {
                    status.postValue(Status.SUCCESS);
                    moviesList.postValue(movies);
                }));
    }

    public MovieProgressStatus addToFavorite(Movie movie) {
        return movieInteractor.addToFavorites(movie);
    }

    public MovieProgressStatus removeFromFavorite(Movie movie) {
        return movieInteractor.removeFromFavorites(movie);
    }

    public void clearFavorites() {
        movieInteractor.clearFavorites();
    }

    public MutableLiveData<Movies> getMovieList() {
        return moviesList;
    }
}
