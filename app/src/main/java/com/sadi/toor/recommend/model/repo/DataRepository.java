package com.sadi.toor.recommend.model.repo;

import com.sadi.toor.recommend.model.api.Api;
import com.sadi.toor.recommend.model.data.Wish;
import com.sadi.toor.recommend.model.data.genre.Genre;
import com.sadi.toor.recommend.model.data.genre.Genres;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.movie.Movies;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.internal.operators.flowable.FlowableAll;
import timber.log.Timber;

@Singleton
public class DataRepository {

    private final Api api;

    @Inject
    public DataRepository(Api api) {
        this.api = api;
    }

    public Flowable<Movies> getMovieLiveList() {
        return getFromNetwork();
    }

    private Flowable<Movies> getFromNetwork() {
        return api.getMovies().toFlowable()
                .doOnError(throwable -> Timber.d("moggot = " + throwable.getMessage()));
    }

    public Flowable<Genres> getGenresList() {
        return api.getGenres().toFlowable()
                .doOnError(throwable -> Timber.d("moggot = " + throwable.getMessage()));
    }

    public Flowable<Movie> sendUserWish(Wish wish) {
        String s1 = wish.getMovies();
        return api.sendUserFavoriteWish(wish.getMovies(), wish.getGenres()).toFlowable()
                .doOnError(throwable -> Timber.d("moggot = " + throwable.getMessage()));
    }
}