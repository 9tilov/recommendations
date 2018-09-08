package com.sadi.toor.recommend.genre.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sadi.toor.recommend.core.wrapper.DataWrapper;
import com.sadi.toor.recommend.core.wrapper.ErrorObject;
import com.sadi.toor.recommend.model.data.Wish;
import com.sadi.toor.recommend.model.data.genre.Genres;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;
import com.sadi.toor.recommend.model.repo.DataRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GenreViewModel extends ViewModel {

    private final MutableLiveData<DataWrapper<Genres>> genres = new MutableLiveData<>();
    private final MutableLiveData<DataWrapper<Recommendations>> movie = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final DataRepository repository;

    @Inject
    GenreViewModel(DataRepository dataRepository) {
        this.repository = dataRepository;
        loadGenres();
    }

    private void loadGenres() {
        compositeDisposable.add(repository.getGenresList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    genres.setValue(new DataWrapper<>(value, null));
                }, t -> {
                    genres.setValue(new DataWrapper<>(null, new ErrorObject(t.getMessage())));
                    Timber.d("moggot = " + t.getMessage());
                }));
    }

    public MutableLiveData<DataWrapper<Genres>> getGenreList() {
        return genres;
    }

    public void sendUserMovies(Wish wish) {
        compositeDisposable.add(repository.sendUserWish(wish)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    movie.setValue(new DataWrapper<>(value, null));
                }, t -> {
                    movie.setValue(new DataWrapper<>(null, new ErrorObject(t.getMessage())));
                    Timber.d("moggot = " + t.getMessage());
                }));
    }

    public void clearRecommendedMovie() {
        movie.setValue(new DataWrapper<>(null, new ErrorObject("Not movie to recommend")));
    }

    public MutableLiveData<DataWrapper<Recommendations>> getRecommendedMovie() {
        return movie;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
