package com.silion.samples.stickylistheaders;

import android.app.Activity;
import android.os.Bundle;

import com.silion.samples.R;

/**
 * Created by silion on 2015/11/13.
 */
public class StickyListHeadersActivity extends Activity {
    private StickyListHeadersListViewWrapper mStickyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_list_headers);
        mStickyListView = (StickyListHeadersListViewWrapper) findViewById(R.id.stickyListView);
        mStickyListView.getListView().setAdapter(new StickyListHeaderAdapter(this));
    }
}
