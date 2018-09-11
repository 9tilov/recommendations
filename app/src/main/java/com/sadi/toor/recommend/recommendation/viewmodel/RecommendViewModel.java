package com.sadi.toor.recommend.recommendation.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.base.Status;
import com.sadi.toor.recommend.model.data.Wish;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;
import com.sadi.toor.recommend.model.repo.DataRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecommendViewModel extends BaseViewModel {

    private final DataRepository repository;
    private final MutableLiveData<Recommendations> recommendations = new MutableLiveData<>();

    @Inject
    RecommendViewModel(DataRepository repository) {
        this.repository = repository;
        addObserver(recommendations);
    }

    public void sendUserMovies(Wish wish) {
        compositeDisposable.add(repository.sendUserWish(wish)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> status.postValue(Status.START_LOADING))
                .doOnError(throwable -> status.postValue(Status.ERROR))
                .retryWhen(retryHandler -> retryHandler.flatMap(retryManager::observeRetries))
                .subscribe(value -> {
                    status.postValue(Status.SUCCESS);
                    recommendations.postValue(value);
                }));
    }

    public MutableLiveData<Recommendations> getRecommendations() {
        return recommendations;
    }
}
