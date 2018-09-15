package com.sadi.toor.recommend.model.data.recommendations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sadi.toor.recommend.model.data.movie.Movie;

import java.util.List;

public class Recommendations {

    @SerializedName("recs")
    @Expose
    private List<Movie> movies;

    public Recommendations(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
