package com.sadi.toor.recommend.filter.interactor;

import android.util.Pair;

import com.sadi.toor.recommend.model.data.Wish;
import com.sadi.toor.recommend.model.data.genre.Genre;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.repo.DataRepository;
import com.sadi.toor.recommend.preparing.interactor.FavoriteMovieController;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import timber.log.Timber;

@Singleton
public class FilterInteractor {

    private FavoriteMovieController favoriteMovieController;
    private DataRepository dataRepository;
    private Filter filter;

    @Inject
    public FilterInteractor(DataRepository dataRepository, FavoriteMovieController favoriteMovieController, Filter filter) {
        this.dataRepository = dataRepository;
        this.favoriteMovieController = favoriteMovieController;
        this.filter = filter;
    }

    public void setFilterPeriod(Pair<Integer, Integer> period) {
        filter.setYearPeriod(period);
    }

    public void setFilterGenres(List<Genre> genres) {
        filter.setGenres(genres);
    }

    public Observable<List<Movie>> getRecommendations() {
        return dataRepository.sendUserWish(new Wish(favoriteMovieController.getFavoritesMovies().toString()));
    }

    public boolean isInYearPeriod(Movie movie) {
        return movie.getYear() >= filter.getYearPeriod().first && movie.getYear() <= filter.getYearPeriod().second;
    }

    public boolean hasGenres(Movie movie) {
        Timber.d("moggot check = " + movie.getGenres().containsAll(filter.getGenres()));
        return movie.getGenres().containsAll(filter.getGenres());
    }
}
