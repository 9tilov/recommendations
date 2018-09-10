package com.sadi.toor.recommend.filter.genre.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.wrapper.DataWrapper;
import com.sadi.toor.recommend.core.wrapper.ErrorObject;
import com.sadi.toor.recommend.model.data.genre.Genres;
import com.sadi.toor.recommend.model.repo.DataRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GenreViewModel extends BaseViewModel {

    private final MutableLiveData<DataWrapper<Genres>> genres = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
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
                .subscribe(value -> {
                    genres.postValue(new DataWrapper<>(value, null));
                }, t -> {
                    genres.postValue(new DataWrapper<>(null, new ErrorObject(t.getMessage())));
                    Timber.d("moggot = " + t.getMessage());
                }));
    }

    public MutableLiveData<DataWrapper<Genres>> getGenreList() {
        return genres;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
