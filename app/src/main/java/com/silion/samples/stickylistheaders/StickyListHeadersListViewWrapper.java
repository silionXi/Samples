package com.silion.samples.stickylistheaders;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.silion.samples.R;

/**
 * Created by silion on 2015/11/13.
 */
public class StickyListHeadersListViewWrapper extends FrameLayout implements OnScrollListener {
    private String className = StickyListHeadersListViewWrapper.class.getSimpleName();
    private int mHeaderBottomPosition;
    private int mHeaderHeight = -1;
    private boolean mAreHeadersSticky;
    private View mHeaderView;
    private ListView mListView;
    private OnScrollListener mOnScrollListener;

    public StickyListHeadersListViewWrapper(Context context) {
        super(context);
        mListView = new ListView(context);
        setAreHeadersSticky(true);
        setup();
    }

    public StickyListHeadersListViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListView = new ListView(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StickyListHeadersListView);
        setAreHeadersSticky(a.getBoolean(0, true));
        a.recycle();
        setup();
    }

    public StickyListHeadersListViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mListView = new ListView(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StickyListHeadersListView);
        setAreHeadersSticky(a.getBoolean(0, true));
        a.recycle();
        setup();
    }

    public void setup() {
        mListView.setOnScrollListener(this);
        addView(mListView);
    }

    public ListView getListView() {
        return mListView;
    }

    public void setListView(ListView listView) {
        mListView = listView;
        mListView.setOnScrollListener(this);
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
                    mHeaderHeight = viewToWatch.findViewById(BaseStickyListHeadersAdapter.HEADER_ID).getHeight();
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
            params.height = mHeaderHeight;
            params.topMargin = mHeaderBottomPosition - mHeaderHeight;
            mHeaderView.setLayoutParams(params);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }
}
