package com.sadi.toor.recommend.filter.genre.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.base.Status;
import com.sadi.toor.recommend.model.data.genre.Genres;
import com.sadi.toor.recommend.model.repo.DataRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GenreViewModel extends BaseViewModel {

    private final MutableLiveData<Genres> genres = new MutableLiveData<>();
    private final DataRepository repository;

    @Inject
    GenreViewModel(DataRepository dataRepository) {
        this.repository = dataRepository;
        addObserver(genres);
        loadGenres();
    }

    private void loadGenres() {
        compositeDisposable.add(repository.getGenresList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> status.postValue(Status.ERROR))
                .retryWhen(retryHandler -> retryHandler.flatMap(retryManager::observeRetries))
                .subscribe(genres::postValue));
    }

    public MutableLiveData<Genres> getGenreList() {
        return genres;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
