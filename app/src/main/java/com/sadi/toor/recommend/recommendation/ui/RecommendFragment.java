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
import android.widget.TextView;

import com.sadi.toor.recommend.Analytics;
import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.Constants;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.filter.ui.FilterFragment;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.recommendation.ui.adapter.RecommendAdapter;
import com.sadi.toor.recommend.recommendation.viewmodel.RecommendViewModel;

import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;
import static com.sadi.toor.recommend.core.base.LoadingStatus.ERROR;
import static com.sadi.toor.recommend.core.base.LoadingStatus.START_LOADING;
import static com.sadi.toor.recommend.core.base.LoadingStatus.SUCCESS;

public class RecommendFragment extends BaseFragment<RecommendViewModel> {

    public static final String TAG = "RecommendFragment";

    @BindView(R.id.rv_rec)
    RecyclerView rvRec;
    @BindView(R.id.rec_btn_rate_again)
    Button btnRateAgain;
    @BindView(R.id.rec_tv_nothing_found)
    TextView tvNothingFound;
    @BindView(R.id.rec_btn_filter)
    AppCompatImageView btnFilter;
    @BindView(R.id.progress_view)
    FrameLayout progress;

    private Snackbar snackbar;

    private RecommendViewModel viewModel;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState, RecommendViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel.getRecommendationData().observe(this, recommendations -> {
            initRecyclerView(recommendations);
            tvNothingFound.setVisibility(recommendations.isEmpty()
                    ? View.VISIBLE
                    : View.GONE);

        });
        viewModel.getStatus().observe(this, status -> {
            switch (status.getType()) {
                case START_LOADING:
                    progress.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progress.setVisibility(View.GONE);
                    break;
                case ERROR:
                    Bundle bundle = new Bundle();
                    bundle.putString(TAG, status.getThrowable().getMessage());
                    analytics.logEvent(Analytics.KEY_NETWORK_REQUEST_ERROR, bundle);
                    progress.setVisibility(View.GONE);
                    this.snackbar = Snackbar.make(view, getString(R.string.error_load_rec), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.retry), action -> viewModel.retryCall());
                    snackbar.show();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown progress status = " + status.getType());
            }
        });
        btnRateAgain.setOnClickListener(v -> {
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, null);
            getActivity().getSupportFragmentManager()
                    .popBackStack();
        });
        btnFilter.setOnClickListener(v -> {
            FilterFragment filterFragment = FilterFragment.newInstance();
            filterFragment.setTargetFragment(RecommendFragment.this, Constants.REC_FILTER_REQUEST_CODE);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out, R.anim.push_down_in, R.anim.push_down_out)
                    .replace(R.id.nav_host_fragment, filterFragment, FilterFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        });

    }

    private void initRecyclerView(List<Movie> movieList) {
        RecommendAdapter adapter = new RecommendAdapter(movieList);
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
    protected String getFragmentTag() {
        return TAG;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.recommend_fragment;
    }

    @Override
    protected boolean onBackPressed() {
        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, null);
        return super.onBackPressed();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.REC_FILTER_REQUEST_CODE) {
                viewModel.getFilteredRecommendations();
            }
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
