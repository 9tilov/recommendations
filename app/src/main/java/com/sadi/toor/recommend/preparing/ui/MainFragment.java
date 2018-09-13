package com.sadi.toor.recommend.preparing.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.movie.Movies;
import com.sadi.toor.recommend.preparing.ui.adapter.CustomLayoutManager;
import com.sadi.toor.recommend.preparing.ui.adapter.MovieAdapter;
import com.sadi.toor.recommend.preparing.viewmodel.MainViewModel;
import com.sadi.toor.recommend.preparing.viewmodel.ProgressStatus;
import com.sadi.toor.recommend.recommendation.ui.RecommendFragment;

import butterknife.BindView;

public class MainFragment extends BaseFragment<MainViewModel> implements MovieAdapter.OnViewClickLister {

    public static final String TAG = "MainFragment";

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
    @BindView(R.id.progress_view)
    FrameLayout progress;

    @Nullable
    private MovieAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private MainViewModel viewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, MainViewModel viewModel) {
        this.viewModel = viewModel;
        initRecyclerView();
        viewModel.getMovieList().observe(this, this::initAdapter);
        viewModel.getStatus().observe(this, status -> {
            switch (status) {
                case START_LOADING:
                    progress.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progress.setVisibility(View.GONE);
                    break;
                case ERROR:
                    progress.setVisibility(View.GONE);
                    Snackbar.make(view, getString(R.string.error_load_movies), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.retry), action -> viewModel.retryCall())
                            .show();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown progress status = " + status.name());
            }
        });
        viewModel.getProgress().observe(this, progressStatus -> {
            if (progressStatus == null) {
                return;
            }
            if (progressStatus.needToStop()) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, RecommendFragment.newInstance(), RecommendFragment.TAG)
                        .addToBackStack(null)
                        .commit();
            } else {
                updateProgress(progressStatus);
            }
        });
        btnBack.setOnClickListener(v -> {
            switchPrevious();
        });
        btnSkip.setOnClickListener(v -> {
            switchNext();
        });
    }

    private void updateProgress(ProgressStatus progress) {
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

    private void initRecyclerView() {
        ((SimpleItemAnimator) rvMovie.getItemAnimator()).setSupportsChangeAnimations(false);
        linearLayoutManager = new CustomLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvMovie);
        rvMovie.setLayoutManager(linearLayoutManager);
    }

    private void initAdapter(Movies movies) {
        Movies movies1 = movies;
        adapter = new MovieAdapter(movies, this);
        rvMovie.setAdapter(adapter);
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
        int prevAdapterPosition = linearLayoutManager.findLastVisibleItemPosition() - 1;
        rvMovie.getLayoutManager().scrollToPosition(prevAdapterPosition);
        if (adapter != null) {
            viewModel.removeFromFavorite(adapter.getMoviewFromPosition(prevAdapterPosition));
        }
    }

    @Override
    public void rate(Movie movie) {
        switchNext();
        viewModel.addToFavorite(movie);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.clearFavorites();
    }
}
