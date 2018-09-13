package com.sadi.toor.recommend.interactor;

import com.sadi.toor.recommend.model.data.Wish;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.movie.Movies;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;
import com.sadi.toor.recommend.model.repo.DataRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class MovieInteractor {

    private DataRepository repository;
    private final List<Movie> favoritesMovies = new ArrayList<>();

    @Inject
    public MovieInteractor(DataRepository repository) {
        this.repository = repository;
    }

    public void addToFavorites(Movie movie) {
        favoritesMovies.add(movie);
    }

    public void removeFromFavorites(Movie movie) {
        if (favoritesMovies.size() > 0 && favoritesMovies.contains(movie)) {
            favoritesMovies.remove(movie);
        }
    }

    public void clearFavorites() {
        favoritesMovies.clear();
    }

    public int getFavoritesSize() {
        return favoritesMovies.size();
    }

    public Observable<Movies> getPrepareMovieList() {
        return repository.getMovieLiveList();
    }

    public Observable<Recommendations> getRecommendations() {
        Movies movies = new Movies();
        movies.setMovies(favoritesMovies);
        return repository.sendUserWish(new Wish(movies.toString()));
    }
}
