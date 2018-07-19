package com.sadi.toor.recommend.view.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.Constants;
import com.sadi.toor.recommend.core.base.BaseActivity;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.databinding.MainFragmentBinding;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.view.adapter.movie.GridItemDecorator;
import com.sadi.toor.recommend.view.adapter.movie.MovieAdapter;
import com.sadi.toor.recommend.viewmodel.MainViewModel;

import androidx.navigation.Navigation;
import timber.log.Timber;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;

public class MainFragment extends BaseFragment<MainViewModel, MainFragmentBinding> implements
        MovieAdapter.ItemSelectedListener, ActionMode.Callback {

    private MovieAdapter adapter;
    private MainFragmentBinding binding;
    private ActionMode actionMode;
    private Menu menu;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MovieAdapter();
        adapter.setOnItemClickListener(this);
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
        initRecyclerView();
        viewModel.getMovieList().observe(this, movies -> {
            if (movies.getData() != null) {
                adapter.setData(movies.getData().getMovies());
                createActionMode();
                Timber.d("Size = " + movies.getData().getMovies().size());
            } else {
                Toast.makeText(getContext(), movies.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        binding.recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new GridItemDecorator(getContext(), HORIZONTAL);
        binding.recyclerView.addItemDecoration(decoration);
    }

    private void createActionMode() {
        if (adapter.getSelectedItemsCount() > 0 && actionMode == null) {
            actionMode = getActivity().startActionMode(this);
            setActionModeValue();
        }
    }

    private void setActionModeValue() {
        if (actionMode == null) {
            return;
        }
        int selectedItemsCount = adapter.getSelectedItemsCount();
        String title = getString(R.string.selected_count, selectedItemsCount, Constants.MIN_MOVIES_SEEN);
        actionMode.setTitle(title);
        menu.findItem(R.id.menu_done).setVisible(selectedItemsCount >= Constants.MIN_MOVIES_SEEN);
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
    public void onItemSelected(Movie item) {
        if (actionMode == null) {
            actionMode = getActivity().startActionMode(this);
        }
        item.setSelected(!item.isSelected());
        setActionModeValue();
        Timber.d("click");
        if (adapter.getSelectedItemsCount() == 0) {
            actionMode.finish();
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_cab_recyclerview, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        sharedViewModel.select(adapter.getSelectedItems());
        Navigation.findNavController(getView()).navigate(R.id.favoritesFragment);
        mode.finish();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        adapter.clearSelection();
    }
}
