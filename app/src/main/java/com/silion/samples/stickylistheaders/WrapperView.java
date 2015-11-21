package com.silion.samples.stickylistheaders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.silion.samples.R;

/**
 * Created by silion on 2015/11/14.
 */
public class WrapperView {
    private LinearLayout mWrapperLinearLayout;

    public WrapperView(Context context) {
        mWrapperLinearLayout = (LinearLayout) LayoutInflater.from(context).
                inflate(R.layout.sticky_list_wrapper, null);
    }

    public WrapperView(View view) {
        mWrapperLinearLayout = (LinearLayout) view;
    }

    public View wrapViews(View... views) {
        mWrapperLinearLayout.removeAllViews();
        for (View view : views) {
            mWrapperLinearLayout.addView(view);
        }
        return mWrapperLinearLayout;
    }
}
