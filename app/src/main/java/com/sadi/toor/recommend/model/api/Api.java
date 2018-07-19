package com.sadi.toor.recommend.model.api;

import com.sadi.toor.recommend.model.data.Wish;
import com.sadi.toor.recommend.model.data.genre.Genres;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.movie.Movies;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("getTopList")
    Single<Movies> getMovies();

    @GET("getGenres")
    Single<Genres> getGenres();

    @POST("getRec")
    Single<Movie> sendUserFavoriteWish(@Query("likes") String movies, @Query("genres") String genres);
}
