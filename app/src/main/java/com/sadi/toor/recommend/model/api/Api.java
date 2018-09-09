package com.sadi.toor.recommend.model.api;

import com.sadi.toor.recommend.model.data.genre.Genres;
import com.sadi.toor.recommend.model.data.movie.Movies;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("getTopList")
    Single<Movies> getMovies();

    @GET("getGenres")
    Single<Genres> getGenres();

    @POST("getRec")
    Single<Recommendations> sendUserFavoriteWish(@Query("likes") String movies);
}
