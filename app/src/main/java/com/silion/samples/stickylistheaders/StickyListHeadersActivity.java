package com.silion.samples.stickylistheaders;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

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
        mStickyListView.getWrappedListView().setAdapter(new StickyListHeaderAdapter(this));
        updateActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
    }
}
