package com.sadi.toor.recommend.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class CustomLayoutManager extends LinearLayoutManager {

    public CustomLayoutManager(Context context, @RecyclerView.Orientation int orientation,
                               boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollHorizontally() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return false;
    }
}
