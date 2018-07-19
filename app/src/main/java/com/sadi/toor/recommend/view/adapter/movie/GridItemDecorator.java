package com.sadi.toor.recommend.view.adapter.movie;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sadi.toor.recommend.core.utils.UiSizeConverter;

public class GridItemDecorator extends DividerItemDecoration {

    public GridItemDecorator(Context context, int orientation) {
        super(context, orientation);
    }

    public void getItemOffsets(Rect outRect,
                               View view,
                               RecyclerView parent,
                               RecyclerView.State state) {
        outRect.top = UiSizeConverter.pxToDp(view.getContext(), 32);
        outRect.bottom = UiSizeConverter.pxToDp(view.getContext(), 32);
        outRect.left = UiSizeConverter.pxToDp(view.getContext(), 16);
        outRect.right = UiSizeConverter.pxToDp(view.getContext(), 16);
    }

}
