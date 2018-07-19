package com.sadi.toor.recommend.view.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.databinding.FavoriteMoviesFragmentBinding;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.view.adapter.favorite.FavoriteAdapter;
import com.sadi.toor.recommend.viewmodel.FavoriteMovieViewModel;

import androidx.navigation.Navigation;
import timber.log.Timber;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class FavoriteMovieFragment extends BaseFragment<FavoriteMovieViewModel, FavoriteMoviesFragmentBinding> implements
        FavoriteAdapter.ItemSelectedListener {

    private FavoriteMoviesFragmentBinding binding;
    private FavoriteAdapter adapter;

    public static FavoriteMovieFragment newInstance() {
        return new FavoriteMovieFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FavoriteAdapter();
        adapter.setOnItemClickListener(this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cab_recyclerview, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Операции для выбранного пункта меню
        switch (item.getItemId()) {
            case R.id.menu_done:
                Navigation.findNavController(getView()).navigate(R.id.genreFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void getBinding(FavoriteMoviesFragmentBinding binding) {
        this.binding = binding;
    }

    @Override
    protected Class<FavoriteMovieViewModel> getViewModel() {
        return FavoriteMovieViewModel.class;
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, FavoriteMovieViewModel viewModel) {
        binding.recyclerViewFavorites.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewFavorites.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        binding.recyclerViewFavorites.addItemDecoration(decoration);
        sharedViewModel.getSelected().observe(this, movie -> {
            adapter.setData(movie);
            Timber.d("moggot size = " + movie.size());
        });
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_title_favorite_movies;
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.favorite_movies_fragment;
    }

    @Override
    public void onItemSelected(Movie item) {
        item.setLiked(!item.isLiked());
    }
}
