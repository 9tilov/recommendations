package com.sadi.toor.recommend.model.repo;

import com.sadi.toor.recommend.model.api.Api;
import com.sadi.toor.recommend.model.data.Wish;
import com.sadi.toor.recommend.model.data.genre.Genres;
import com.sadi.toor.recommend.model.data.movie.Movies;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import timber.log.Timber;

@Singleton
public class DataRepository {

    private final Api api;

    @Inject
    public DataRepository(Api api) {
        this.api = api;
    }

    public Observable<Movies> getMovieLiveList() {
        return getFromNetwork().toObservable();
    }

    private Single<Movies> getFromNetwork() {
        return api.getMovies()
                .doOnError(throwable -> Timber.d("moggot = " + throwable.getMessage()));
    }

    public Observable<Genres> getGenresList() {
        return api.getGenres()
                .doOnError(throwable -> Timber.d("moggot = " + throwable.getMessage()));
    }

    public Observable<Recommendations> sendUserWish(Wish wish) {
        return api.sendUserFavoriteWish(wish.getMovies())
                .doOnError(throwable -> Timber.d("moggot = " + throwable.getMessage()));
    }
}
