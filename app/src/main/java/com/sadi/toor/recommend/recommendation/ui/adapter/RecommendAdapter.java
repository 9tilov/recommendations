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
import com.sadi.toor.recommend.model.data.genre.Genre;
import com.sadi.toor.recommend.model.data.movie.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {

    private final List<Movie> movies;

    public RecommendAdapter(@NonNull List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item, parent, false);
        return new RecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class RecommendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rec_iv_poster)
        ImageView ivPoster;
        @BindView(R.id.rec_tv_title)
        TextView tvTitle;
        @BindView(R.id.rec_tv_genre)
        TextView tvGenre;
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
            StringBuilder sb = new StringBuilder();
            for (Genre genre : data.getGenres()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(genre.getGenreName());
            }
            tvGenre.setText(sb.toString());
            tvOverview.setText(data.getDescription());
        }
    }
}
