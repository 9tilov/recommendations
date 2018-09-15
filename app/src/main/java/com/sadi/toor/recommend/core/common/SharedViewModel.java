package com.sadi.toor.recommend.core.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Pair;

import com.sadi.toor.recommend.core.utils.DateUtils;
import com.sadi.toor.recommend.filter.interactor.Filter;
import com.sadi.toor.recommend.model.data.genre.Genre;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;

import java.util.List;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> selectedMovies = new MutableLiveData<>();
    private final MutableLiveData<Recommendations> recommendations = new MutableLiveData<>();
    private final MutableLiveData<List<Genre>> selectedGenres = new MutableLiveData<>();
    private final MutableLiveData<Pair<Integer, Integer>> selectedPeriod = new MutableLiveData<>();
    private final MutableLiveData<Filter> filterData = new MutableLiveData<>();

    public SharedViewModel() {
        selectedPeriod.setValue(new Pair<>(DateUtils.DEFAULT_YEAR, DateUtils.getCurrentYear()));
    }

    public void putWatchedMovies(List<Movie> item) {
        selectedMovies.setValue(item);
    }

    public void putRecommendations(Recommendations item) {
        recommendations.setValue(item);
    }

    public void putGenres(List<Genre> items) {
        selectedGenres.setValue(items);
    }

    public void putPeriod(Pair<Integer, Integer> item) {
        selectedPeriod.setValue(item);
    }

    public void putFilter(Filter filter) {
        filterData.setValue(filter);
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

    public LiveData<Pair<Integer, Integer>> getSelectedPeriod() {
        return selectedPeriod;
    }

    public MutableLiveData<Filter> getFilterData() {
        return filterData;
    }
}
