package com.sadi.toor.recommend.preparing.interactor;

import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.movie.Movies;
import com.sadi.toor.recommend.model.repo.DataRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class MovieInteractor {

    private DataRepository repository;
    private FavoriteMovieController favoriteMovieController;

    @Inject
    public MovieInteractor(DataRepository repository,
                           FavoriteMovieController favoriteMovieController) {
        this.repository = repository;
        this.favoriteMovieController = favoriteMovieController;
    }

    public MovieProgressStatus addToFavorites(Movie movie) {
        return favoriteMovieController.addMovie(movie);
    }

    public MovieProgressStatus removeFromFavorites(Movie movie) {
        return favoriteMovieController.removeMovie(movie);
    }

    public void clearFavorites() {
        favoriteMovieController.clearMovies();
    }

    public Observable<Movies> getPrepareMovieList() {
        return repository.getMovieLiveList();
    }
}
