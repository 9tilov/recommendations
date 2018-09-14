package com.sadi.toor.recommend.interactor;

import com.sadi.toor.recommend.model.data.movie.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FavoriteMovieController {

    private final List<Movie> favoritesMovies = new ArrayList<>();
    private MovieProgressStatus movieProgressStatus = new MovieProgressStatus();

    @Inject
    public FavoriteMovieController() {
    }

    public MovieProgressStatus addMovie(Movie movie) {
        favoritesMovies.add(movie);
        movieProgressStatus.increaseProgress();
        return movieProgressStatus;
    }

    public MovieProgressStatus removeMovie(Movie movie) {
        if (favoritesMovies.remove(movie)) {
            movieProgressStatus.decreaseProgress();
        }
        return movieProgressStatus;
    }

    public MovieProgressStatus clearMovies() {
        favoritesMovies.clear();
        movieProgressStatus.resetProgress();
        return movieProgressStatus;
    }

    public List<Movie> getFavoritesMovies() {
        return favoritesMovies;
    }
}
