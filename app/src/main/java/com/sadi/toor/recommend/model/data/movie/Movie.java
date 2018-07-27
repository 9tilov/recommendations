package com.sadi.toor.recommend.model.data.movie;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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

    private boolean isSelected = false;
    private boolean isLiked = false;

    public long getMovieId() {
        return movieId;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
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
}