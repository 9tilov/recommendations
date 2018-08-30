package com.sadi.toor.recommend.model.data.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("movie_id")
    @Expose
    private long movieId;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("year")
    @Expose
    private int year;
    @SerializedName("trailer")
    @Expose
    private String trailer;

    public long getMovieId() {
        return movieId;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getTrailer() {
        return trailer;
    }
}