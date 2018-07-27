package com.sadi.toor.recommend.view.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.view.adapter.CustomLayoutManager;
import com.sadi.toor.recommend.view.adapter.movie.GridItemDecorator;
import com.sadi.toor.recommend.view.adapter.movie.MovieAdapter;
import com.sadi.toor.recommend.viewmodel.MainViewModel;

import androidx.navigation.Navigation;
import butterknife.BindView;
import timber.log.Timber;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;

public class MainFragment extends BaseFragment<MainViewModel> implements MovieAdapter.OnViewClickLister {

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.btn_skip)
    TextView btnSkip;
    @BindView(R.id.iv_back)
    AppCompatImageView btnBack;

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
        Timber.d("moggot create");
        this.viewModel = viewModel;
        viewModel.clearFavorites();
        viewModel.getMovieList().observe(this, movies -> {
            if (movies.getData() != null) {
                adapter = new MovieAdapter(movies.getData().getMovies(), this);
                initRecyclerView();
                Timber.d("Size = " + movies.getData().getMovies().size());
            } else {
                Toast.makeText(getContext(), movies.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        btnBack.setOnClickListener(v -> {
            switchPrevious();
            viewModel.removeFromFavorite();
        });
        btnSkip.setOnClickListener(v -> switchNext());
    }

    private void initRecyclerView() {
        rvMovie.setAdapter(adapter);

        ((SimpleItemAnimator) rvMovie.getItemAnimator()).setSupportsChangeAnimations(false);
        linearLayoutManager = new CustomLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvMovie);

        rvMovie.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new GridItemDecorator(getContext(), HORIZONTAL);
        rvMovie.addItemDecoration(decoration);
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
        rvMovie.getLayoutManager().scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
    }

    private void switchPrevious() {
        rvMovie.getLayoutManager().scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() - 1);
    }

    @Override
    public void rate(Movie movie) {
        if (viewModel.addToFavorite(movie)) {
            Navigation.findNavController(getView()).navigate(R.id.genreFragment);
        } else {
            new Handler().postDelayed(this::switchNext, 200);
        }
    }
}
