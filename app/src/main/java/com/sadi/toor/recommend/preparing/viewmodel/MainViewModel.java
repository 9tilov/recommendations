package com.sadi.toor.recommend.preparing.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.base.LoadingStatus;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.preparing.interactor.MovieInteractor;
import com.sadi.toor.recommend.preparing.interactor.MovieProgressStatus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.sadi.toor.recommend.core.base.LoadingStatus.ERROR;
import static com.sadi.toor.recommend.core.base.LoadingStatus.START_LOADING;
import static com.sadi.toor.recommend.core.base.LoadingStatus.SUCCESS;

public class MainViewModel extends BaseViewModel {

    private final MutableLiveData<List<Movie>> moviesList = new MutableLiveData<>();
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
                    status.postValue(new LoadingStatus(START_LOADING));
                })
                .doOnError(throwable -> {
                    status.postValue(new LoadingStatus(ERROR, throwable));
                })
                .retryWhen(retryHandler -> retryHandler.flatMap(retryManager::observeRetries))
                .subscribe(movies -> {
                    status.postValue(new LoadingStatus(SUCCESS));
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

    public MutableLiveData<List<Movie>> getMovieList() {
        return moviesList;
    }
}
