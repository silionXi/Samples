package com.silion.samples.stickylistheaders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

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
public class WrapperView {
    private LinearLayout mWrapperLinearLayout;

    public WrapperView(Context context) {
        mWrapperLinearLayout = (LinearLayout) LayoutInflater.from(context).
                inflate(R.layout.sticky_list_wrapper, null);
    }

    public WrapperView(View view) {
        mWrapperLinearLayout = (LinearLayout) view;
    }

    public View wrapViews(View... views) {
        mWrapperLinearLayout.removeAllViews();
        for (View view : views) {
            mWrapperLinearLayout.addView(view);
        }
        return mWrapperLinearLayout;
    }
}
