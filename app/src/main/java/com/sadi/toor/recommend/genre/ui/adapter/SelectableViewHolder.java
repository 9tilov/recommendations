package com.sadi.toor.recommend.genre.ui.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.model.data.genre.SelectableGenre;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SelectableViewHolder extends RecyclerView.ViewHolder {

    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;
    private final AppCompatImageView ivCheck;
    private final OnItemSelectedListener itemSelectedListener;
    TextView textView;
    SelectableGenre item;


    public SelectableViewHolder(View view, OnItemSelectedListener listener) {
        super(view);
        itemSelectedListener = listener;
        textView = (TextView) view.findViewById(R.id.genre_tv_genre_name);
        ivCheck = (AppCompatImageView) view.findViewById(R.id.genre_iv_genre_check);
        view.setOnClickListener(view1 -> {
            setChecked(!item.isSelected() || getItemViewType() != MULTI_SELECTION);
            itemSelectedListener.onItemSelected(item);

        });
    }

    public void setChecked(boolean value) {
        item.setSelected(value);
        ivCheck.setVisibility(value ? VISIBLE : INVISIBLE);
    }

    public interface OnItemSelectedListener {

        void onItemSelected(SelectableGenre item);
    }

}
