package com.sadi.toor.recommend.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sadi.toor.recommend.core.wrapper.DataWrapper;
import com.sadi.toor.recommend.core.wrapper.ErrorObject;
import com.sadi.toor.recommend.model.data.movie.Movies;
import com.sadi.toor.recommend.model.repo.DataRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<DataWrapper<Movies>> movies = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final DataRepository repository;

    @Inject
    MainViewModel(DataRepository dataRepository) {
        this.repository = dataRepository;
        loadMovies();
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

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
