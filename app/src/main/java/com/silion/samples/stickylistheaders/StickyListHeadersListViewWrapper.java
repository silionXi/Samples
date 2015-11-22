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
