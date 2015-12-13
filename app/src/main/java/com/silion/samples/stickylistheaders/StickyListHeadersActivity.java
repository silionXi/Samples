package com.silion.samples.stickylistheaders;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

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
