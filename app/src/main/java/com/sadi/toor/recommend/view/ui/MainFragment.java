package com.sadi.toor.recommend.view.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.Toast;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.databinding.MainFragmentBinding;
import com.sadi.toor.recommend.view.adapter.CustomLayoutManager;
import com.sadi.toor.recommend.view.adapter.movie.GridItemDecorator;
import com.sadi.toor.recommend.view.adapter.movie.MovieAdapter;
import com.sadi.toor.recommend.viewmodel.MainViewModel;

import timber.log.Timber;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;

public class MainFragment extends BaseFragment<MainViewModel, MainFragmentBinding> implements MovieAdapter.OnViewClickLister {

    private MovieAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private MainFragmentBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected void getBinding(MainFragmentBinding binding) {
        this.binding = binding;
    }

    @Override
    protected Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, MainViewModel viewModel) {
        Timber.d("moggot create");
        viewModel.getMovieList().observe(this, movies -> {
            if (movies.getData() != null) {
                adapter = new MovieAdapter(movies.getData().getMovies(), this);
                initRecyclerView();
                Timber.d("Size = " + movies.getData().getMovies().size());
            } else {
                Toast.makeText(getContext(), movies.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        binding.rvMovie.setAdapter(adapter);

        ((SimpleItemAnimator) binding.rvMovie.getItemAnimator()).setSupportsChangeAnimations(false);
        linearLayoutManager = new CustomLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvMovie);

        binding.rvMovie.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new GridItemDecorator(getContext(), HORIZONTAL);
        binding.rvMovie.addItemDecoration(decoration);
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

    @Override
    public void skip() {
        binding.rvMovie.getLayoutManager().scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
    }

    @Override
    public void back() {
        binding.rvMovie.getLayoutManager().scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() - 1);
    }
}
