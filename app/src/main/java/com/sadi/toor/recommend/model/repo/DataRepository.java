package com.sadi.toor.recommend.model.repo;

import com.sadi.toor.recommend.model.api.Api;
import com.sadi.toor.recommend.model.data.Wish;
import com.sadi.toor.recommend.model.data.genre.Genre;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.user.User;

import java.util.List;

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

    public Observable<User> login(String token) {
        return api.login(token).toObservable();
    }

    public Observable<List<Movie>> getMovieLiveList() {
        return getFromNetwork().toObservable();
    }

    private Single<List<Movie>> getFromNetwork() {
        return api.getMovies()
                .doOnError(throwable -> Timber.d("moggot = " + throwable.getMessage()));
    }

    public Observable<List<Genre>> getGenresList() {
        return api.getGenres()
                .doOnError(throwable -> Timber.d("moggot = " + throwable.getMessage()));
    }

    public Observable<List<Movie>> sendUserWish(Wish wish) {
        return api.sendUserFavoriteWish(wish.getMovies())
                .doOnError(throwable -> Timber.d("moggot = " + throwable.getMessage()));
    }
}
