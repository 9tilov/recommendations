package com.sadi.toor.recommend.core.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;

import java.util.List;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> selected = new MutableLiveData<>();
    private final MutableLiveData<Recommendations> recommendations = new MutableLiveData<>();

    public void putWatchedMovies(List<Movie> item) {
        selected.setValue(item);
    }

    public void putRecommendations(Recommendations item) {
        recommendations.setValue(item);
    }

    public LiveData<List<Movie>> getSelected() {
        return selected;
    }

    public LiveData<Recommendations> getRecommendations() {
        return recommendations;
    }

}
