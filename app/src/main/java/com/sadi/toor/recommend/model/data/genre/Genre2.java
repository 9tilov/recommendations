package com.sadi.toor.recommend.model.data.genre;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre2 {

    @SerializedName("id")
    @Expose
    private long genreId;
    @SerializedName("name")
    @Expose
    private String genreName;

    public Genre2(long genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public long getGenreId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre2)) return false;

        Genre2 genre2 = (Genre2) o;

        if (getGenreId() != genre2.getGenreId()) return false;
        return getGenreName() != null ? getGenreName().equals(genre2.getGenreName()) : genre2.getGenreName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getGenreId() ^ (getGenreId() >>> 32));
        result = 31 * result + (getGenreName() != null ? getGenreName().hashCode() : 0);
        return result;
    }
}
