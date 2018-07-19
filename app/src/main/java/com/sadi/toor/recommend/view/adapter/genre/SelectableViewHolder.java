package com.sadi.toor.recommend.view.adapter.genre;

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
    TextView textView;
    private AppCompatImageView ivCheck;
    SelectableGenre item;
    private OnItemSelectedListener itemSelectedListener;


    public SelectableViewHolder(View view, OnItemSelectedListener listener) {
        super(view);
        itemSelectedListener = listener;
        textView = (TextView) view.findViewById(R.id.tv_genre_name);
        ivCheck = (AppCompatImageView) view.findViewById(R.id.iv_genre_check);
        view.setOnClickListener(view1 -> {
            if (item.isSelected() && getItemViewType() == MULTI_SELECTION) {
                setChecked(false);
            } else {
                setChecked(true);
            }
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
