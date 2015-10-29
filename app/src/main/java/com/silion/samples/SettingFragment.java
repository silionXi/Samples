package com.silion.samples;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link PreferenceFragment} subclass.
 */
public class SettingFragment extends PreferenceFragment implements IFragmentBase {
    private MainActivity mMainActivity;
    private View mRootView;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_setting, container, false);
        Button mainButton = (Button) mRootView.findViewById(R.id.main);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.pushFragment(new MainFragment());
            }
        });
        Button lockButton = (Button) mRootView.findViewById(R.id.lock);
        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.pushFragment(new LockPatternFragment());
            }
        });
        return mRootView;
    }


    @Override
    public void updataActionBar() {
        ActionBar actionBar = mMainActivity.getActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.show();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setTitle(mMainActivity.getString(R.string.setting_fragment_title));
    }

    @Override
    public void onBackPressed() {
        if (mMainActivity != null) {
            mMainActivity.popFragment();
        }
    }
}
