package com.app.horizontalsteps.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by gleb on 7/18/17.
 */

public class ObservableHorizontalScrollView  extends HorizontalScrollView {

    public interface OnScrollChangedListener {
        void onScrollChanged(ObservableHorizontalScrollView view, int l, int t);
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    public ObservableHorizontalScrollView(Context context) {
        super(context);
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t);
        }
    }
}
