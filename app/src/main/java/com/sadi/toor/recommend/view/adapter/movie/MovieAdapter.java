package com.sadi.toor.recommend.view.adapter.movie;

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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> data = new ArrayList<>();
    private ItemSelectedListener listener;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public MovieAdapter() {
    }

    public void setData(List<Movie> data) {
        this.data.clear();
        this.data.addAll(data);
        for (int i = 0; i < data.size(); ++i) {
            if (data.get(i).isSelected()) {
                selectedItems.put(i, true);
            }
        }
        notifyDataSetChanged();
    }

    public List<Movie> getData() {
        return data;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.movie_item, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(binding);
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
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        listener.onItemSelected(movie);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    public void clearSelection() {
        for (Movie item : data) {
            item.setSelected(false);
        }
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemsCount() {
        return selectedItems.size();
    }

    public List<Movie> getSelectedItems() {
        List<Movie> selectedMovies = new ArrayList<>();
        for (Movie item : data) {
            if (item.isSelected()) {
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

    public void setOnItemClickListener(ItemSelectedListener listener) {
        this.listener = listener;
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

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
