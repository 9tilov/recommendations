package com.sadi.toor.recommend.filter.interactor;

import android.util.Pair;

import com.sadi.toor.recommend.core.utils.DateUtils;
import com.sadi.toor.recommend.model.data.genre.Genre;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Filter {

    private Pair<Integer, Integer> yearPeriod = new Pair<>(DateUtils.MIN_YEAR, DateUtils.getCurrentYear());
    private List<Genre> genres = new ArrayList<>();

    @Inject
    public Filter() {

    }

    public void setYearPeriod(Pair<Integer, Integer> yearPeriod) {
        this.yearPeriod = yearPeriod;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Pair<Integer, Integer> getYearPeriod() {
        return yearPeriod;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}
