package com.silion.samples.stickylistheaders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.silion.samples.R;

/**
 * Created by silion on 2015/11/17.
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
