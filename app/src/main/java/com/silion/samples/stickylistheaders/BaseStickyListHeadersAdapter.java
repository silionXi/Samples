package com.silion.samples.stickylistheaders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by silion on 2015/11/14.
 */
public abstract class BaseStickyListHeadersAdapter extends BaseAdapter {
    public static final int LIST_ITEM_ID = 0;
    public static final int HEADER_ID = 0;

    private List<View> mHeaderCacheList;
    private List<WrapperView> mWrapperViewCacheList;
    private Context mContext;
    private HashMap<Integer, View> mCurrentlyVissibleHeaderViews;

    public BaseStickyListHeadersAdapter(Context context) {
        mContext = context;
        mHeaderCacheList = new ArrayList<>();
        mWrapperViewCacheList = new ArrayList<>();
        mCurrentlyVissibleHeaderViews = new HashMap<>();
    }

    public abstract View getHeaderView(int position, View convertView);

    /**
     * @param position the list position
     * @return an identifier for this header, a header for a position must always have a constant ID
     */
    public abstract long getHeaderId(int position);

    protected abstract View getView(int position, View convertView);

    public View getHeaderWithForPosition(int position) {
        View header = null;
        if (mHeaderCacheList.size() > 0) {
            header = mHeaderCacheList.remove(0);
        }
        header = getHeaderView(position, header);
        header.setId(HEADER_ID);
        return header;
    }

    public View attachHeaderToListItem(View header, View listItem) {
        listItem.setId(LIST_ITEM_ID);
        WrapperView wrapperView = null;
        if (mWrapperViewCacheList.size() > 0) {
            wrapperView = mWrapperViewCacheList.remove(0);
        }
        if (wrapperView == null) {
            wrapperView = new WrapperView(mContext);
        }
        header.setClickable(true);
        header.setFocusable(false);
        return wrapperView.wrapViews(header, listItem);
    }

    public View wrapListItem(View listItem) {
        listItem.setId(LIST_ITEM_ID);
        WrapperView wrapperView = null;
        if (mWrapperViewCacheList.size() > 0) {
            wrapperView = mWrapperViewCacheList.remove(0);
        }
        if (wrapperView == null) {
            wrapperView = new WrapperView(mContext);
        }
        return wrapperView.wrapViews(listItem);
    }

    public View axtractHeaderAndListItemFromConvertView(View convertView) {
        if (convertView == null) {
            return null;
        }
        if (mCurrentlyVissibleHeaderViews.containsValue(convertView)) {
            mCurrentlyVissibleHeaderViews.remove(convertView.getTag());
        }
        ViewGroup viewGroup = (ViewGroup) convertView;
        View headerView = viewGroup.findViewById(HEADER_ID);
        if (headerView != null) {
            mHeaderCacheList.add(headerView);
        }

        View listItem = viewGroup.findViewById(LIST_ITEM_ID);
        viewGroup.removeAllViews();
        mWrapperViewCacheList.add(new WrapperView(convertView));

        return listItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = getView(position, axtractHeaderAndListItemFromConvertView(convertView));
        if (position == 0 || getHeaderId(position) != getHeaderId(position-1)) {
            view = attachHeaderToListItem(getHeaderWithForPosition(position), view);
            mCurrentlyVissibleHeaderViews.put(position, view);
        } else {
            view = wrapListItem(view);
        }
        view.setTag(position);
        return view;
    }

    public Context getContext() {
        return mContext;
    }

    public HashMap<Integer, View> getCurrentlyVissibleHeaderViews() {
        return mCurrentlyVissibleHeaderViews;
    }
}
