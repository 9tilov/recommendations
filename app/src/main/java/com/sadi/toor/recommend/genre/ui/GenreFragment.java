package com.sadi.toor.recommend.genre.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.genre.ui.adapter.SelectableAdapter;
import com.sadi.toor.recommend.genre.ui.adapter.SelectableViewHolder;
import com.sadi.toor.recommend.genre.viewmodel.GenreViewModel;
import com.sadi.toor.recommend.model.data.Wish;
import com.sadi.toor.recommend.model.data.genre.Genres;
import com.sadi.toor.recommend.model.data.genre.SelectableGenre;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.movie.Movies;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.Navigation;
import butterknife.BindView;
import timber.log.Timber;

public class GenreFragment extends BaseFragment<GenreViewModel> implements SelectableViewHolder.OnItemSelectedListener {

    @BindView(R.id.recycler_view_genre)
    RecyclerView recyclerViewGenre;
    @BindView(R.id.btn_genre)
    TextView btnSend;
    @BindView(R.id.genre_progress)
    AVLoadingIndicatorView progressBar;

    private SelectableAdapter adapter;
    private List<Movie> movies = new ArrayList<>();

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
        sharedViewModel.getSelected().observe(this, movie -> {
            movies = movie;
            Timber.d("moggot size = " + movie.size());
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewGenre.addItemDecoration(decoration);
        recyclerViewGenre.setLayoutManager(layoutManager);
        viewModel.getGenreList().observe(this, movies -> {
            if (movies.getData() != null) {
                adapter = new SelectableAdapter(this, movies.getData().getGenres(), true);
                btnSend.setVisibility(View.VISIBLE);
                recyclerViewGenre.setAdapter(adapter);
                Timber.d("Size = " + movies.getData().getGenres().size());
            } else {
                Toast.makeText(getContext(), movies.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.clearRecommendedMovie();
        viewModel.getRecommendedMovie().observe(this, movies -> {
            if (movies.getData() != null) {
                sharedViewModel.putRecommendations(movies.getData());
                Navigation.findNavController(getView()).navigate(R.id.recommendFragment);
            }
        });

        btnSend.setOnClickListener(view1 -> {
            Movies movies = new Movies(this.movies);
            Genres genres = new Genres(adapter.getSelectedItems());
            viewModel.sendUserMovies(new Wish(movies.toString(), genres.toString()));
            progressBar.setVisibility(View.VISIBLE);
            btnSend.setVisibility(View.INVISIBLE);
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
    public void onItemSelected(SelectableGenre item) {
        Timber.d("Select = " + item.getGenreName());
    }
}
