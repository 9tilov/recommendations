package com.sadi.toor.recommend.preparing.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sadi.toor.recommend.core.wrapper.DataWrapper;
import com.sadi.toor.recommend.core.wrapper.ErrorObject;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.movie.Movies;
import com.sadi.toor.recommend.model.repo.DataRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<DataWrapper<Movies>> movies = new MutableLiveData<>();
    private final MutableLiveData<ProgressStatus> progressData = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final DataRepository repository;
    private final List<Movie> favoritesMovie = new ArrayList<>();
    private ProgressStatus progressStatus;

    @Inject
    MainViewModel(DataRepository dataRepository) {
        this.repository = dataRepository;
        this.progressStatus = new ProgressStatus();
        progressStatus.setChosenMovies(favoritesMovie.size());
        progressData.setValue(progressStatus);
        loadMovies();
    }

    public void clearFavorites() {
        favoritesMovie.clear();
        progressStatus.setChosenMovies(favoritesMovie.size());
    }

    private void loadMovies() {
        compositeDisposable.add(repository.getMovieLiveList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    movies.setValue(new DataWrapper<>(value, null));
                }, t -> {
                    movies.setValue(new DataWrapper<>(null, new ErrorObject(t.getMessage())));
                    Timber.d("moggot = " + t.getMessage());
                }));
    }

    public MutableLiveData<DataWrapper<Movies>> getMovieList() {
        return movies;
    }

    public MutableLiveData<ProgressStatus> getProgress() {
        return progressData;
    }

    public void addToFavorite(Movie movie) {
        favoritesMovie.add(movie);
        Timber.d("moggot size= " + favoritesMovie.size());
        progressStatus.setChosenMovies(favoritesMovie.size());
    }

    public List<Movie> getFavoritesMovie() {
        return favoritesMovie;
    }

    public void removeFromFavorite() {
        if (favoritesMovie.size() > 0) {
            favoritesMovie.remove(favoritesMovie.size() - 1);
            progressStatus.setChosenMovies(favoritesMovie.size());
        }
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
