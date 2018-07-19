package com.sadi.toor.recommend.view.adapter.favorite;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sadi.toor.recommend.BR;
import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.model.data.movie.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteMovieViewHoler> {

    private List<Movie> data = new ArrayList<>();
    private FavoriteAdapter.ItemSelectedListener listener;
    private SparseBooleanArray favoriteMovies = new SparseBooleanArray();

    public FavoriteAdapter() {
    }

    public void setData(List<Movie> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<Movie> getData() {
        return data;
    }

    @Override
    public FavoriteAdapter.FavoriteMovieViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.favorite_movie_item, parent, false);
        FavoriteAdapter.FavoriteMovieViewHoler viewHolder = new FavoriteAdapter.FavoriteMovieViewHoler(binding);
        binding.getRoot().setOnClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Movie movie = data.get(adapterPosition);
                toogleSelection(movie, adapterPosition);
                notifyItemChanged(adapterPosition);
            }
        });
        return viewHolder;
    }

    private void toogleSelection(Movie movie, int pos) {
        if (favoriteMovies.get(pos, false)) {
            favoriteMovies.delete(pos);
        } else {
            favoriteMovies.put(pos, true);
        }
        listener.onItemSelected(movie);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteMovieViewHoler holder, int position) {
        holder.bind(data.get(position));
    }

    public void clearSelection() {
        for (Movie item : data) {
            item.setLiked(false);
        }
        favoriteMovies.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemsCount() {
        return favoriteMovies.size();
    }

    public List<Movie> getFavoriteMovies() {
        List<Movie> selectedMovies = new ArrayList<>();
        for (Movie item : data) {
            if (item.isLiked()) {
                selectedMovies.add(item);
            }
        }
        return selectedMovies;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemSelectedListener {
        void onItemSelected(Movie item);
    }

    public void setOnItemClickListener(FavoriteAdapter.ItemSelectedListener listener) {
        this.listener = listener;
    }

    static class FavoriteMovieViewHoler extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        FavoriteMovieViewHoler(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.binding = dataBinding;
        }

        void bind(Movie data) {
            binding.setVariable(BR.movie, data);
            binding.executePendingBindings();
        }
    }
}
