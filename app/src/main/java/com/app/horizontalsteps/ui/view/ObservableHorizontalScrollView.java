package com.app.horizontalsteps.ui.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.app.horizontalsteps.R;

/**
 * Created by gleb on 7/18/17.
 */

public class ObservableHorizontalScrollView  extends HorizontalScrollView implements ViewTreeObserver.OnPreDrawListener {

    public ObservableHorizontalScrollView(Context context) {
        super(context);
        setup();
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        getViewTreeObserver().addOnPreDrawListener(this);
    }

    @Override
    public boolean onPreDraw() {
        getViewTreeObserver().removeOnPreDrawListener(this);

        LinearLayout child = (LinearLayout) getChildAt(0);

        int width = getWidth();
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(width / 2, LinearLayout.LayoutParams.MATCH_PARENT);

        View leftSpacer = new View(getContext());
        leftSpacer.setLayoutParams(p);
        child.addView(leftSpacer, 0);

        View rightSpacer = new View(getContext());
        rightSpacer.setLayoutParams(p);
        child.addView(rightSpacer);

        return false;
    }

    public void scrollToTheEnd() {
        postDelayed(new Runnable() {
            public void run() {
                fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
    }

    public void scrollToView(final View view) {
        postDelayed(new Runnable() {
            public void run() {
                int dim = (int) getContext().getResources().getDimension(R.dimen.buttonPaddingOffset);
                Point childOffset = new Point();
                getDeepChildOffset(view.getParent(), view, childOffset);
                smoothScrollTo(childOffset.x - dim, 0);
            }
        }, 100L);
    }

    private void getDeepChildOffset(ViewParent parent, View child, Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        if (parentGroup.equals(this)) {
            return;
        }
        getDeepChildOffset(parentGroup.getParent(), parentGroup, accumulatedOffset);
    }
}
