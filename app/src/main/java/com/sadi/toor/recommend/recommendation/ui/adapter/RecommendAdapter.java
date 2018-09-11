package com.sadi.toor.recommend.recommendation.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.model.data.movie.Movie;
import com.sadi.toor.recommend.model.data.recommendations.Recommendations;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {

    private final Recommendations movies;
    private final OnViewClickLister clickLister;

    public RecommendAdapter(@NonNull Recommendations movies, OnViewClickLister clickLister) {
        this.movies = movies;
        this.clickLister = clickLister;
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item, parent, false);
        RecommendViewHolder viewHolder = new RecommendViewHolder(view);
        viewHolder.ivPoster.setOnClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Movie movie = movies.getMovies().get(adapterPosition);
                clickLister.showTrailer(movie);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {
        holder.bind(movies.getMovies().get(position));
    }

    @Override
    public int getItemCount() {
        return movies.getMovies().size();
    }

    public interface OnViewClickLister {
        void showTrailer(Movie movie);
    }

    static class RecommendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rec_iv_poster)
        ImageView ivPoster;
        @BindView(R.id.rec_tv_title)
        TextView tvTitle;
        @BindView(R.id.rec_tv_overview)
        TextView tvOverview;
        @BindView(R.id.rec_cv)
        CardView cardView;

        RecommendViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(Movie data) {
            Glide.with(ivPoster)
                    .load(data.getLink())
                    .apply(new RequestOptions().transforms(new CenterCrop()))
                    .into(ivPoster);
            tvTitle.setText(itemView.getResources().getString(R.string.rec_title_with_year, data.getName(), data.getYear()));
            tvOverview.setText(data.getDescription());
        }
    }
}
