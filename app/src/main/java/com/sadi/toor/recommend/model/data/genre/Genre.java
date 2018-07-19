package com.sadi.toor.recommend.model.data.genre;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("genre_id")
    @Expose
    private long genreId;
    @SerializedName("genre_name")
    @Expose
    private String genreName;

    public Genre(long id, String name) {
        this.genreId = id;
        this.genreName = name;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;
        Genre itemCompare = (Genre) obj;
        return itemCompare.genreId == this.genreId;
    }
}
