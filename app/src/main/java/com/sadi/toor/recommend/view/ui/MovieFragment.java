package com.sadi.toor.recommend.view.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.viewmodel.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends BaseFragment<MainViewModel> {

    public static final String ARGUMENT_MOVIE_KEY = "movie_argument";

    @BindView(R.id.iv_main_movie_pic)
    ImageView ivMoviePic;
    @BindView(R.id.tv_movie_title)
    TextView tvTitle;
    @BindView(R.id.tv_movie_year)
    TextView tvYear;

    private Movie movie;

    public static MovieFragment newInstance(Movie movie) {
        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_MOVIE_KEY, movie);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movie = (Movie) getArguments().getParcelable(ARGUMENT_MOVIE_KEY);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_fragment, container, false);
    }

    @Override
    protected Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, MainViewModel viewModel) {
        ButterKnife.bind(this, view);
        Glide.with(ivMoviePic)
                .setDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.ic_launcher_background))
                .asBitmap()
                .load(movie.getLink())
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(20)))
                .into(ivMoviePic);
        tvTitle.setText(movie.getName());
        tvYear.setText(String.valueOf(movie.getYear()));
    }

    @Override
    @StringRes
    protected int getTitle() {
        return R.string.fragment_title_choose_movies;
    }

    @Override
    protected boolean showBackButton() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.movie_fragment;
    }
}
