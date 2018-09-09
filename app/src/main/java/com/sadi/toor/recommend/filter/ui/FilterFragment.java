package com.sadi.toor.recommend.filter.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.filter.viewmodel.FilterViewModel;
import com.sadi.toor.recommend.model.data.genre.Genre;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import butterknife.BindView;

public class FilterFragment extends BaseFragment<FilterViewModel> {

    @BindView(R.id.filter_cv_genre)
    CardView cvGenre;
    @BindView(R.id.filter_tv_genre)
    TextView tvGenre;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, FilterViewModel viewModel) {
        cvGenre.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(R.id.genreFragment,
                null,
                new NavOptions.Builder()
                        .setEnterAnim(R.anim.slide_in_right)
                        .setExitAnim(R.anim.slide_out_left)
                        .setPopEnterAnim(R.anim.slide_in_left)
                        .setPopExitAnim(R.anim.slide_out_right)
                        .build()));
        sharedViewModel.getSelectedGenres().observe(this, genres -> {
            StringBuilder sb = new StringBuilder();
            for (Genre genre : genres) {
                sb.append(genre.getGenreName());
                sb.append(", ");
            }
            sb.delete(sb.length() - 2, sb.length() - 1);
            tvGenre.setText(sb.toString());
        });
    }

    @Override
    protected Class<FilterViewModel> getViewModel() {
        return FilterViewModel.class;
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_title_filter;
    }

    @Override
    protected boolean showBackButton() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.filter_fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                Navigation.findNavController(getView()).popBackStack();
                return true;
        }
        return false;
    }

}
