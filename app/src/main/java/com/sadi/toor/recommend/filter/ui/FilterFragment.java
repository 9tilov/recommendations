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
import com.sadi.toor.recommend.filter.genre.ui.GenreFragment;
import com.sadi.toor.recommend.filter.viewmodel.FilterViewModel;
import com.sadi.toor.recommend.filter.year.ui.YearFragment;
import com.sadi.toor.recommend.model.data.genre.Genre;

import butterknife.BindView;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class FilterFragment extends BaseFragment<FilterViewModel> {

    public static final String TAG = "FilterFragment";

    @BindView(R.id.filter_cv_genre)
    CardView cvGenre;
    @BindView(R.id.filter_cv_year)
    CardView cvYear;
    @BindView(R.id.filter_tv_genre)
    TextView tvGenre;
    @BindView(R.id.filter_tv_year)
    TextView tvYear;

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, FilterViewModel viewModel) {
        cvGenre.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.nav_host_fragment, GenreFragment.newInstance(), GenreFragment.TAG)
                .addToBackStack(null)
                .commit());

        cvYear.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(R.id.nav_host_fragment, YearFragment.newInstance(), YearFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        });
        sharedViewModel.getSelectedGenres().observe(this, genres -> {
            if (genres == null) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (Genre genre : genres) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(genre.getGenreName());
            }
            tvGenre.setText(sb.length() > 0 ? sb.toString() : getString(R.string.any));
        });
        sharedViewModel.getSelectedPeriod().observe(this, periodPair -> {
            if (periodPair != null) {
                tvYear.setText(periodPair.first.equals(periodPair.second)
                        ? String.valueOf(periodPair.first)
                        : getString(R.string.time_period, periodPair.first, periodPair.second));
            }
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
    protected boolean onBackPressed() {
        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, null);
        Timber.d("moggot bbbbact");
        return super.onBackPressed();
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
                getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, null);
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
        }
        return false;
    }

}
