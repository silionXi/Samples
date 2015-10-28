package com.silion.samples;


import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link FragmentBase} subclass.
 */
public class MainFragment extends FragmentBase {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mTitle = MainFragment.class.getSimpleName();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void updataActionBar() {
        ActionBar actionBar = mMainActivity.getActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.show();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        super.updataActionBar();
    }
}
