package com.sadi.toor.recommend.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sadi.toor.recommend.model.data.movie.Movie;

import java.util.List;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> selected = new MutableLiveData<>();

    public void select(List<Movie> item) {
        selected.setValue(item);
    }

    public LiveData<List<Movie>> getSelected() {
        return selected;
    }
}
