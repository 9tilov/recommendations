package com.sadi.toor.recommend.model.data.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sadi.toor.recommend.model.data.genre.Genre2;

import java.util.List;

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
    @SerializedName("overview")
    @Expose
    private String description;
    @SerializedName("genres")
    @Expose
    private List<Genre2> genres;
    private float rating;

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

    public String getDescription() {
        return description;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Genre2> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", rating=" + rating +
                '}';
    }
}