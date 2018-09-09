package com.sadi.toor.recommend.preparing.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.movie.Movies;
import com.sadi.toor.recommend.preparing.ui.adapter.CustomLayoutManager;
import com.sadi.toor.recommend.preparing.ui.adapter.MovieAdapter;
import com.sadi.toor.recommend.preparing.viewmodel.MainViewModel;
import com.sadi.toor.recommend.preparing.viewmodel.ProgressStatus;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import butterknife.BindView;
import timber.log.Timber;

public class MainFragment extends BaseFragment<MainViewModel> implements MovieAdapter.OnViewClickLister {

    @BindView(R.id.main_rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.main_btn_skip)
    TextView btnSkip;
    @BindView(R.id.main_iv_back)
    AppCompatImageView btnBack;
    @BindView(R.id.main_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.main_tv_progress_count)
    TextView tvProgressCount;

    private MovieAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private MainViewModel viewModel;
    private Movies movies = new Movies();

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
        this.viewModel = viewModel;
        viewModel.clearFavorites();
        initRecyclerView();
        viewModel.getMovieList().observe(this, movieList -> {
            if (movieList.getData() != null) {
                movies.setMovies(movieList.getData().getMovies());
                adapter = new MovieAdapter(movies, this);
                rvMovie.setAdapter(adapter);
                Timber.d("Size = " + movieList.getData().getMovies().size());
            } else {
                Toast.makeText(getContext(), movieList.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        btnBack.setOnClickListener(v -> {
            switchPrevious();
        });
        btnSkip.setOnClickListener(v -> {
            switchNext();
        });

        viewModel.getProgress().observe(this, progressStatus -> {
            if (progressStatus.needToStop()) {
                sharedViewModel.putWatchedMovies(viewModel.getFavoritesMovie());
                Navigation.findNavController(getView()).navigate(R.id.recommendFragment,
                        null,
                        new NavOptions.Builder()
                                .setEnterAnim(R.anim.slide_in_right)
                                .setExitAnim(R.anim.slide_out_left)
                                .setPopEnterAnim(R.anim.slide_in_left)
                                .setPopExitAnim(R.anim.slide_out_right)
                                .build());
            } else {
                setProgress(progressStatus);
            }
        });
    }

    private void initRecyclerView() {
        ((SimpleItemAnimator) rvMovie.getItemAnimator()).setSupportsChangeAnimations(false);
        linearLayoutManager = new CustomLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvMovie);

        rvMovie.setLayoutManager(linearLayoutManager);
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
        if (rvMovie != null) {
            rvMovie.getLayoutManager().scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
            btnBack.setVisibility(View.VISIBLE);
        }
    }

    private void switchPrevious() {
        btnBack.setVisibility(View.INVISIBLE);
        viewModel.removeFromFavorite(adapter.getMoviewFromPosition(linearLayoutManager.findLastVisibleItemPosition() - 1));
        rvMovie.getLayoutManager().scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() - 1);
    }

    @Override
    public void rate(Movie movie) {
        switchNext();
        viewModel.addToFavorite(movie);
    }

    private void setProgress(ProgressStatus progress) {
        tvProgressCount.setText(getString(R.string.main_progress, progress.getChosenMovies(), progress.getMovieCountToChoose()));
        ValueAnimator progressAnimation = ValueAnimator.ofInt(progressBar.getProgress(), progress.getProgress());
        progressAnimation.setInterpolator(new LinearOutSlowInInterpolator());
        progressAnimation.addUpdateListener(animation -> {
            if (animation != null && progressBar != null) {
                progressBar.setProgress((int) animation.getAnimatedValue());
            }
        });
        progressAnimation.start();
    }
}
