package com.sadi.toor.recommend.filter.viewmodel;

import android.util.Pair;

import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.filter.interactor.FilterInteractor;
import com.sadi.toor.recommend.model.data.genre.Genre;

import java.util.List;

import javax.inject.Inject;

public class FilterViewModel extends BaseViewModel {

    private FilterInteractor filterInteractor;

    @Inject
    FilterViewModel(FilterInteractor filterInteractor) {
        this.filterInteractor = filterInteractor;
    }

    public void setFilterPeriod(Pair<Integer, Integer> period) {
        filterInteractor.setFilterPeriod(period);
    }

    public void setGenreList(List<Genre> genreList) {
        filterInteractor.setFilterGenres(genreList);
    }
}
