package com.silion.samples.recyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by silion on 2015/11/4.
 */
public class RecyclerData {
    private Context mContext;
    private Drawable mIcon;
    private String mTitle;

    public RecyclerData(Context context, int icon, int title) {
        this.mContext = context;
        this.mIcon = context.getResources().getDrawable(icon);
        this.mTitle = context.getString(title);
    }

    public Drawable getmIcon() {
        return mIcon;
    }

    public String getmTitle() {
        return mTitle;
    }
}
