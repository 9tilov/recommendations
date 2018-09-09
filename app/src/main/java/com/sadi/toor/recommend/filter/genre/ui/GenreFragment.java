package com.sadi.toor.recommend.filter.genre.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.filter.genre.ui.adapter.SelectableAdapter;
import com.sadi.toor.recommend.filter.genre.ui.adapter.SelectableViewHolder;
import com.sadi.toor.recommend.filter.genre.viewmodel.GenreViewModel;
import com.sadi.toor.recommend.model.data.genre.Genre;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import timber.log.Timber;

public class GenreFragment extends BaseFragment<GenreViewModel> implements SelectableViewHolder.OnItemSelectedListener {

    @BindView(R.id.recycler_view_genre)
    RecyclerView recyclerViewGenre;

    private SelectableAdapter adapter;

    public static GenreFragment newInstance() {
        return new GenreFragment();
    }

    @Override
    protected Class<GenreViewModel> getViewModel() {
        return GenreViewModel.class;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, GenreViewModel viewModel) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewGenre.addItemDecoration(decoration);
        recyclerViewGenre.setLayoutManager(layoutManager);
        viewModel.getGenreList().observe(this, genres -> {
            if (genres.getData() != null) {
                sharedViewModel.getSelectedGenres().observe(this, selectedGenres -> {
                    Set<Genre> genreSet = new HashSet<>(selectedGenres);
                    for (Genre genre: genres.getData().getGenres()) {
                        if (genreSet.contains(genre)) {
                            genre.setSelected(true);
                        }
                    }
                });
                adapter = new SelectableAdapter(this, genres.getData().getGenres(), true);
                recyclerViewGenre.setAdapter(adapter);
                Timber.d("Size = " + genres.getData().getGenres().size());
            } else {
                Toast.makeText(getContext(), genres.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_title_genre;
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.genre_fragment;
    }

    @Override
    public void onItemSelected(Genre item) {
        sharedViewModel.putGenres(adapter.getSelectedItems());
        Timber.d("Select = " + adapter.getSelectedItems());
    }
}
