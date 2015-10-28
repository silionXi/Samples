package com.silion.samples;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBase extends Fragment implements IFragmentBase{
    protected MainActivity mMainActivity;
    protected String mTitle;
    protected View mCustomActionBarView;

    public FragmentBase() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mMainActivity = (MainActivity) activity;
    }

    protected void performActionLink(String actionLink) {
        mMainActivity.performActionLink(actionLink);
    }


    @Override
    public void updataActionBar() {
        ActionBar actionBar = mMainActivity.getActionBar();
        if (actionBar == null) {
            return;
        }

        int displayOptions = actionBar.getDisplayOptions();

        if ((displayOptions & ActionBar.DISPLAY_SHOW_TITLE) > 0) {
            actionBar.setTitle(mTitle);
        }

        if ((displayOptions & ActionBar.DISPLAY_SHOW_CUSTOM) > 0) {
            //TODO custom actionbar view
        }
    }

    @Override
    public void onBackPressed() {
        if (mMainActivity != null) {
            mMainActivity.popFragment();
        }
    }
}
