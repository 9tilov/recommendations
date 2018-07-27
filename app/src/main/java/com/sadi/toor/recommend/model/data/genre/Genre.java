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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Genre)) {
            return false;
        }

        Genre genre = (Genre) o;

        if (getGenreId() != genre.getGenreId()) {
            return false;
        }
        return getGenreName() != null ? getGenreName().equals(genre.getGenreName()) : genre.getGenreName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getGenreId() ^ (getGenreId() >>> 32));
        result = 31 * result + (getGenreName() != null ? getGenreName().hashCode() : 0);
        return result;
    }
}
