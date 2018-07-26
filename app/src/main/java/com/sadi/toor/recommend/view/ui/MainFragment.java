package com.sadi.toor.recommend.view.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.view.ui.adapter.MovieFragmentAdapter;
import com.sadi.toor.recommend.view.ui.view.CustomViewPager;
import com.sadi.toor.recommend.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainFragment extends BaseFragment<MainViewModel> {

    private MovieFragmentAdapter adapter;
    private MainViewModel viewModel;
    @BindView(R.id.vp_movie)
    CustomViewPager vpMovie;
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.btn_skip)
    TextView tvSkip;
    @BindView(R.id.rb_rating)
    RatingBar ratingBar;

    private List<Movie> movies = new ArrayList<>();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, MainViewModel viewModel) {
        Timber.d("moggot create");
        ButterKnife.bind(this, view);
        this.viewModel = viewModel;
        vpMovie.setPagingEnabled(false);
        viewModel.getMovieList().observe(this, movies -> {
            if (movies.getData() != null) {
                this.movies = movies.getData().getMovies();
                adapter = new MovieFragmentAdapter(getActivity().getSupportFragmentManager(), this.movies);
                initRecyclerView();
                Timber.d("Size = " + movies.getData().getMovies().size());
            } else {
                Toast.makeText(getContext(), movies.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        tvSkip.setOnClickListener(v -> {
            switchNext();
        });
        ivBack.setOnClickListener(v -> {
            switchPrevious();
        });
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (viewModel.addToFavorite(movies.get(vpMovie.getCurrentItem()))) {
                Navigation.findNavController(getView()).navigate(R.id.genreFragment);
            } else {
                switchNext();
            }
        });
    }

    private void initRecyclerView() {
        vpMovie.setAdapter(adapter);
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_title_choose_movies;
    }

    @Override
    protected boolean showBackButton() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.main_fragment;
    }

    private void switchNext() {
        vpMovie.setCurrentItem(vpMovie.getCurrentItem() + 1);
    }

    private void switchPrevious() {
        vpMovie.setCurrentItem(vpMovie.getCurrentItem() - 1);
    }


}
