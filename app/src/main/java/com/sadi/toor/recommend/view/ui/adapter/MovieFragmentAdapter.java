package com.sadi.toor.recommend.view.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.view.ui.MovieFragment;

import java.util.List;

public class MovieFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Movie> movies;

    public MovieFragmentAdapter(FragmentManager fragmentManager, List<Movie> movieList) {
        super(fragmentManager);
        this.movies = movieList;
    }

    @Override
    public Fragment getItem(int position) {
        return MovieFragment.newInstance(movies.get(position));
    }

    @Override
    public int getCount() {
        return movies.size();
    }
}

