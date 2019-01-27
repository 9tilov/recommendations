package com.sadi.toor.recommend.model.api;

import com.sadi.toor.recommend.model.data.genre.Genre;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.user.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("login")
    Single<User> login(@Query("access_token") String token);

    @GET("getTopList")
    Single<List<Movie>> getMovies();

    @GET("getGenres")
    Observable<List<Genre>> getGenres();

    @POST("getRec")
    Observable<List<Movie>> sendUserFavoriteWish(@Query("likes") String movies);
}
