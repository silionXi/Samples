package com.silion.samples.stickylistheaders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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
public class StickyListHeaderAdapter extends BaseStickyListHeadersAdapter {
    private String[] mCountryArray;
    private LayoutInflater mLayoutInflater;

    public StickyListHeaderAdapter(Context context) {
        super(context);
        mLayoutInflater = LayoutInflater.from(context);
        mCountryArray = context.getResources().getStringArray(R.array.countries);

    }

    @Override
    public View getHeaderView(int position, View convertView) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.sticky_list_header, null);
            holder.mHeaderTextView = (TextView) convertView.findViewById(R.id.headerTextView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        holder.mHeaderTextView.setText(mCountryArray[position].subSequence(0, 1));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return mCountryArray[position].subSequence(0, 1).charAt(0);
    }

    @Override
    protected View getView(int position, View convertView) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.sticky_list_item, null);
            holder.mTextView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(mCountryArray[position]);
        return convertView;
    }

    @Override
    public int getCount() {
        return mCountryArray.length;
    }

    @Override
    public Object getItem(int position) {
        return mCountryArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class HeaderViewHolder {
        TextView mHeaderTextView;
    }

    class ViewHolder {
        TextView mTextView;
    }
}
