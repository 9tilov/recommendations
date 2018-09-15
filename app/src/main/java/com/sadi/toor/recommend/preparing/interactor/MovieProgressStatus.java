package com.sadi.toor.recommend.preparing.interactor;

public class MovieProgressStatus {

    public static final int MOVIE_COUNT_TO_CHOOSE = 10;
    private int chosenMovies = 0;

    public void increaseProgress() {
        chosenMovies++;
    }

    public void decreaseProgress() {
        chosenMovies--;
    }

    public void resetProgress() {
        chosenMovies = 0;
    }

    public int getProgress() {
        return 100 * chosenMovies / MOVIE_COUNT_TO_CHOOSE;
    }

    public boolean isComplete() {
        return chosenMovies == MOVIE_COUNT_TO_CHOOSE;
    }

    public int getChosenMovies() {
        return chosenMovies;
    }
}
