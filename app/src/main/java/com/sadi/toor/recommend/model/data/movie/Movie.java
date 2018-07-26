package com.sadi.toor.recommend.model.data.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

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

    private float rate;

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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.movieId);
        dest.writeString(this.link);
        dest.writeString(this.name);
        dest.writeInt(this.year);
        dest.writeFloat(this.rate);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.movieId = in.readLong();
        this.link = in.readString();
        this.name = in.readString();
        this.year = in.readInt();
        this.rate = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}