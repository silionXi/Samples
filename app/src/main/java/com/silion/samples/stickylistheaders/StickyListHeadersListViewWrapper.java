package com.silion.samples.stickylistheaders;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.silion.samples.R;

/**
 *
 * @author Emil lslshadow@163.com
 *
 *
Copyright 2012 Emil silion

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 *
 */
public class StickyListHeadersListViewWrapper extends FrameLayout implements OnScrollListener {
    private String className = StickyListHeadersListViewWrapper.class.getSimpleName();

    private static final String HEADER_HEIGHT = "headerHeight";
    private static final String SUPER_INSTANCE_STATE = "superInstanceState";
    private int mHeaderBottomPosition;
    private int mHeaderHeight = -1;
    private boolean mAreHeadersSticky;
    private View mHeaderView;
    private ListView mListView;
    private OnScrollListener mOnScrollListener;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public StickyListHeadersListViewWrapper(Context context) {
        super(context);
        mListView = new ListView(context);
        setAreHeadersSticky(true);
        setup();
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p/>
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see #StickyListHeadersListViewWrapper(Context, AttributeSet, int)
     */
    public StickyListHeadersListViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListView = new ListView(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StickyListHeadersListView);
        setAreHeadersSticky(a.getBoolean(0, true));
        a.recycle();
        setup();
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of View allows subclasses to use their
     * own base style when they are inflating. For example, a Button class's
     * constructor would call this version of the super class constructor and
     * supply <code>R.attr.buttonStyle</code> for <var>defStyleAttr</var>; this
     * allows the theme's button style to modify all of the base view attributes
     * (in particular its background) as well as the Button class's attributes.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     * @see #StickyListHeadersListViewWrapper(Context, AttributeSet) (Context, AttributeSet)
     */
    public StickyListHeadersListViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mListView = new ListView(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StickyListHeadersListView);
        setAreHeadersSticky(a.getBoolean(0, true));
        a.recycle();
        setup();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle instanceState = new Bundle();
        instanceState.putInt(HEADER_HEIGHT, mHeaderHeight);
        instanceState.putParcelable(SUPER_INSTANCE_STATE, super.onSaveInstanceState());
        return instanceState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        mHeaderHeight = ((Bundle)state).getInt(HEADER_HEIGHT);
        super.onRestoreInstanceState(((Bundle)state).getParcelable(SUPER_INSTANCE_STATE));
    }

    public void setup() {
        mListView.setOnScrollListener(this);
        mListView.setId(R.id.list_view);
        addView(mListView);
    }

    public ListView getWrappedListView() {
        return mListView;
    }

    public void setWrappedListView(ListView listView) {
        mListView.setOnScrollListener(this);
        mListView = listView;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    public void setAreHeadersSticky(boolean areHeadersSticky) {
        mAreHeadersSticky = areHeadersSticky;
    }

    public boolean areHeadersSticky() {
        return mAreHeadersSticky;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        if (mListView.getAdapter() == null) {
            return;
        }
        if (!(mListView.getAdapter() instanceof BaseStickyListHeadersAdapter)) {
            throw new IllegalArgumentException("Adapter must be a subclass of StickyListHeadersAdapter");
        }
        BaseStickyListHeadersAdapter stickyListHeadersAdapter = (BaseStickyListHeadersAdapter) mListView.getAdapter();
        if (mAreHeadersSticky) {
            View viewToWatch = stickyListHeadersAdapter.getCurrentlyVissibleHeaderViews().get(firstVisibleItem);
            if (viewToWatch == null) {
                viewToWatch = stickyListHeadersAdapter.getCurrentlyVissibleHeaderViews().get(firstVisibleItem + 1);
            }
            if (viewToWatch != null) {
                if (mHeaderHeight < 0) {
                    mHeaderHeight = viewToWatch.findViewById(R.id.header_view).getHeight();
                }
                mHeaderBottomPosition = Math.min(viewToWatch.getTop(), mHeaderHeight);
                mHeaderBottomPosition = mHeaderBottomPosition < 0 ? mHeaderHeight : mHeaderBottomPosition;
            } else {
                mHeaderBottomPosition = mHeaderHeight;
            }
            mHeaderView = stickyListHeadersAdapter.getHeaderView(firstVisibleItem, mHeaderView);
            if (getChildCount() > 1) {
                removeViewAt(1);
            }
            addView(mHeaderView);
            LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
            params.height = 57;
            params.topMargin = mHeaderBottomPosition - mHeaderHeight;
            params.gravity = 0;
            mHeaderView.setLayoutParams(params);
            mHeaderView.setVisibility(VISIBLE);
        }else {
            if (mHeaderView != null) {
                mHeaderView.setVisibility(GONE);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }
}
