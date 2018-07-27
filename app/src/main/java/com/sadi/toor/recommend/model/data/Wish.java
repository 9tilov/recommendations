package com.sadi.toor.recommend.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Wish {

    @JsonProperty("likes")
    private final String movies;
    @JsonProperty("genres")
    private final String genres;

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
