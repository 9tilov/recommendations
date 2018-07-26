package com.sadi.toor.recommend.model.data.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {

    @SerializedName("movies")
    @Expose
    private List<Movie> movies;

    public Movies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
