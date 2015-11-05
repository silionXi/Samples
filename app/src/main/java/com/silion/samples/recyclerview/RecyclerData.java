package com.silion.samples.recyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by silion on 2015/11/4.
 */
public class RecyclerData {
    private Drawable mIcon;
    private String mTitle;
    private int mOffsetHeight;

    public RecyclerData(Context context, int icon, String title) {
        this(context, icon, title, 0);
    }

    public RecyclerData(Context context, int icon, String title, int offsetHeight) {
        this.mIcon = context.getResources().getDrawable(icon);
        this.mTitle = title;
        this.mOffsetHeight = offsetHeight;
    }

    public Drawable getmIcon() {
        return mIcon;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmOffsetHeight() {
        return mOffsetHeight;
    }
}
