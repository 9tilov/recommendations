package com.sadi.toor.recommend.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SnapHelper;
import android.view.View;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;
import com.sadi.toor.recommend.view.adapter.recommend.RecommendAdapter;
import com.sadi.toor.recommend.viewmodel.RecommendViewModel;

import butterknife.BindView;
import timber.log.Timber;

public class RecommendFragment extends BaseFragment<RecommendViewModel> implements RecommendAdapter.OnViewClickLister {

    @BindView(R.id.rv_rec)
    RecyclerView rvRec;

    private RecommendViewModel viewModel;
    private RecommendAdapter adapter;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, RecommendViewModel viewModel) {
        this.viewModel = viewModel;
        sharedViewModel.getRecommendations().observe(this, recommendations -> {
            initRecyclerView(recommendations);
            Timber.d("moggot size = " + recommendations.getMovies().size());
        });
    }

    private void initRecyclerView(Recommendations recommendations) {
        adapter = new RecommendAdapter(recommendations, this);
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
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.recommend_fragment;
    }

    @Override
    public void showTrailer(Movie movie) {
        startActivity(new Intent(getActivity(), PlayerActivity.class));
    }
}
