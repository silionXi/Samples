package com.silion.samples;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBase extends Fragment implements IFragmentBase {
    protected final Handler mHandler = new Handler(Looper.getMainLooper());
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

    @Override
    public void onDetach() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDetach();
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
        actionBar.show();
    }

    @Override
    public void onBackPressed() {
        if (mMainActivity != null) {
            mMainActivity.popFragment();
        }
    }
}
