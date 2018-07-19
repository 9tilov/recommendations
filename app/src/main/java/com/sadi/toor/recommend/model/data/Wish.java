package com.sadi.toor.recommend.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sadi.toor.recommend.model.data.genre.Genres;
import com.sadi.toor.recommend.model.data.movie.Movies;

import java.util.Arrays;

public class Wish {

    @JsonProperty("likes")
    private String movies;
    @JsonProperty("genres")
    private String genres;

    @JsonCreator
    public Wish(@JsonProperty("likes") String movies,
                @JsonProperty("genres") String genres) {
        this.movies = movies;
        this.genres = genres;
    }

    public String getMovies() {
        return movies;
    }

    public String getGenres() {
        return genres;
    }
}
