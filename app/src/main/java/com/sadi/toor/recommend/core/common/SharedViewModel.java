package com.sadi.toor.recommend.core.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sadi.toor.recommend.model.data.genre.Genre;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;

import java.util.List;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> selectedMovies = new MutableLiveData<>();
    private final MutableLiveData<Recommendations> recommendations = new MutableLiveData<>();
    private final MutableLiveData<List<Genre>> selectedGenres = new MutableLiveData<>();

    public void putWatchedMovies(List<Movie> item) {
        selectedMovies.setValue(item);
    }

    public void putRecommendations(Recommendations item) {
        recommendations.setValue(item);
    }

    public void putGenres(List<Genre> items) {
        selectedGenres.setValue(items);
    }

    public LiveData<List<Movie>> getSelectedMovies() {
        return selectedMovies;
    }

    public LiveData<Recommendations> getRecommendations() {
        return recommendations;
    }

    public LiveData<List<Genre>> getSelectedGenres() {
        return selectedGenres;
    }

}
