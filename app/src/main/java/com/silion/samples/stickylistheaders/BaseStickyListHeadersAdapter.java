package com.silion.samples.stickylistheaders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.silion.samples.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public abstract class BaseStickyListHeadersAdapter extends BaseAdapter {
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
        header.setId(R.id.header_view);
        return header;
    }

    public View attachHeaderToListItem(View header, View listItem) {
        listItem.setId(R.id.list_item_view);
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
        listItem.setId(R.id.list_item_view);
        WrapperView wrapperView = null;
        if (mWrapperViewCacheList.size() > 0) {
            wrapperView = mWrapperViewCacheList.remove(0);
        }
        if (wrapperView == null) {
            wrapperView = new WrapperView(mContext);
        }
        return wrapperView.wrapViews(listItem);
    }

    /**
     * puts header into mHeaderCacheList, wrapper into mWrapperViewCacheList and returns listItem
     * if convertView is null, returns null
     */
    public View axtractHeaderAndListItemFromConvertView(View convertView) {
        if (convertView == null) {
            return null;
        }
        if (mCurrentlyVissibleHeaderViews.containsValue(convertView)) {
            mCurrentlyVissibleHeaderViews.remove(convertView.getTag());
        }
        ViewGroup viewGroup = (ViewGroup) convertView;
        View headerView = viewGroup.findViewById(R.id.header_view);
        if (headerView != null) {
            mHeaderCacheList.add(headerView);
        }

        View listItem = viewGroup.findViewById(R.id.list_item_view);
        mWrapperViewCacheList.add(new WrapperView(convertView));

        viewGroup.removeAllViews();

        return listItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = getView(position, axtractHeaderAndListItemFromConvertView(convertView));
        if (position == 0 || getHeaderId(position) != getHeaderId(position - 1)) {
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
