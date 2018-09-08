package com.sadi.toor.recommend.preparing.viewmodel;

public class ProgressStatus {

    private final int movieCountToChoose = 10;
    private int chosenMovies = 0;

    public int getProgress() {
        return 100 * chosenMovies / movieCountToChoose;
    }

    public int getChosenMovies() {
        return chosenMovies;
    }

    public void setChosenMovies(int chosenMovies) {
        this.chosenMovies = chosenMovies;
    }

    public int getMovieCountToChoose() {
        return movieCountToChoose;
    }

    public boolean needToStop() {
        return chosenMovies >= movieCountToChoose;
    }
}
