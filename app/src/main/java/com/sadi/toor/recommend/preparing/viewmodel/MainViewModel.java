package com.sadi.toor.recommend.preparing.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.base.Status;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.movie.Movies;
import com.sadi.toor.recommend.model.repo.DataRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

    private final MutableLiveData<Movies> moviesList = new MutableLiveData<>();
    private final MutableLiveData<ProgressStatus> progressData = new MutableLiveData<>();

    private final DataRepository repository;
    private final List<Movie> favoritesMovie = new ArrayList<>();
    private ProgressStatus progressStatus;

    @Inject
    MainViewModel(DataRepository dataRepository) {
        this.repository = dataRepository;
        this.progressStatus = new ProgressStatus();
        addObserver(moviesList);
        addObserver(progressData);
        progressStatus.setChosenMovies(favoritesMovie.size());
        progressData.postValue(progressStatus);
        loadMovies();
    }

    private void loadMovies() {
        compositeDisposable.add(repository.getMovieLiveList()
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

    public void addToFavorite(Movie movie) {
        favoritesMovie.add(movie);
        progressStatus.setChosenMovies(favoritesMovie.size());
        progressData.postValue(progressStatus);
    }

    public void removeFromFavorite(Movie movie) {
        if (favoritesMovie.size() > 0 && favoritesMovie.contains(movie)) {
            favoritesMovie.remove(movie);
            progressStatus.setChosenMovies(favoritesMovie.size());
            progressData.postValue(progressStatus);
        }
    }

    public void clearFavorites() {
        favoritesMovie.clear();
        progressStatus.setChosenMovies(favoritesMovie.size());
        progressData.postValue(progressStatus);
    }

    public MutableLiveData<Movies> getMovieList() {
        return moviesList;
    }

    public MutableLiveData<ProgressStatus> getProgress() {
        return progressData;
    }

    public List<Movie> getFavoritesMovie() {
        return favoritesMovie;
    }
}
