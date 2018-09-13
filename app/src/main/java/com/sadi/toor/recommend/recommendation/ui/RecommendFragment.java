package com.sadi.toor.recommend.recommendation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.Constants;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.filter.ui.FilterFragment;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;
import com.sadi.toor.recommend.player.PlayerActivity;
import com.sadi.toor.recommend.recommendation.ui.adapter.RecommendAdapter;
import com.sadi.toor.recommend.recommendation.viewmodel.RecommendViewModel;

import butterknife.BindView;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class RecommendFragment extends BaseFragment<RecommendViewModel> implements RecommendAdapter.OnViewClickLister {

    public static final String TAG = "RecommendFragment";

    @BindView(R.id.rv_rec)
    RecyclerView rvRec;
    @BindView(R.id.rec_btn_rate_again)
    Button btnRateAgain;
    @BindView(R.id.rec_btn_filter)
    AppCompatImageView btnFilter;
    @BindView(R.id.progress_view)
    FrameLayout progress;

    Snackbar snackbar;

    private RecommendViewModel viewModel;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, RecommendViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.getRecommendations().observe(this, this::initRecyclerView);
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
                    this.snackbar = Snackbar.make(view, getString(R.string.error_load_rec), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.retry), action -> viewModel.retryCall());
                    snackbar.show();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown progress status = " + status.name());
            }
        });
        btnRateAgain.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .popBackStack();
        });
        btnFilter.setOnClickListener(v -> {
            FilterFragment filterFragment = FilterFragment.newInstance();
            filterFragment.setTargetFragment(RecommendFragment.this, Constants.REC_FILTER_REQUEST_CODE);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out, R.anim.push_down_in, R.anim.push_down_out)
                    .replace(R.id.nav_host_fragment, filterFragment, FilterFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        });

    }

    private void initRecyclerView(Recommendations recommendations) {
        RecommendAdapter adapter = new RecommendAdapter(recommendations.getMovies(), this);
        rvRec.setAdapter(adapter);
        ((SimpleItemAnimator) rvRec.getItemAnimator()).setSupportsChangeAnimations(false);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelper = new PagerSnapHelper();
        rvRec.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(rvRec);
        rvRec.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected Class<RecommendViewModel> getViewModel() {
        return RecommendViewModel.class;
    }

    @Override
    protected int getTitle() {
        return R.string.recommend_title;
    }

    @Override
    protected boolean showBackButton() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.recommend_fragment;
    }

    @Override
    public void showTrailer(Movie movie) {
        startActivity(new Intent(getActivity(), PlayerActivity.class));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.REC_FILTER_REQUEST_CODE)
                Timber.d("moggot back!!");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }
}
