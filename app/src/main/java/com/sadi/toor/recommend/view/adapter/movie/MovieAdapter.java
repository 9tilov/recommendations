package com.sadi.toor.recommend.view.adapter.movie;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sadi.toor.recommend.BR;
import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.model.data.movie.Movie;

import java.util.List;

import io.reactivex.annotations.NonNull;
import timber.log.Timber;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private OnViewClickLister clickLister;

    public MovieAdapter(@NonNull List<Movie> movies, OnViewClickLister clickLister) {
        this.movies = movies;
        this.clickLister = clickLister;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.movie_item, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(binding);
        binding.getRoot().setOnClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(adapterPosition);
                notifyItemChanged(adapterPosition);
            }
        });

        TextView tvSkip = binding.getRoot().findViewById(R.id.btn_skip);
        tvSkip.setOnClickListener(v -> {
            clickLister.skip();
            Timber.d("moggot click");

        });

        AppCompatImageView back = binding.getRoot().findViewById(R.id.iv_back);
        back.setOnClickListener(v -> {
            clickLister.back();
            Timber.d("moggot click");

        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnViewClickLister {
        void skip();

        void back();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        MovieViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.binding = dataBinding;
        }

        void bind(Movie data) {
            binding.setVariable(BR.movie, data);
            binding.executePendingBindings();
        }
    }
}
