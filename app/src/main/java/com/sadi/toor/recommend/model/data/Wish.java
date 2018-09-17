package com.sadi.toor.recommend.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Wish {

    @JsonProperty("likes")
    private final String movies;

    @JsonCreator
    public Wish(@JsonProperty("likes") String movies) {
        this.movies = movies;
    }

    public String getMovies() {
        return movies;
    }
}
