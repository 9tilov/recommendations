package com.sadi.toor.recommend.recommendation.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.sadi.toor.recommend.model.repo.DataRepository;

import javax.inject.Inject;

public class RecommendViewModel extends ViewModel {

    private final DataRepository dataRepository;

    @Inject
    RecommendViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }
}
