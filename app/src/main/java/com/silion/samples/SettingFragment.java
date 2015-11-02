package com.silion.samples;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link PreferenceFragment} subclass.
 */
public class SettingFragment extends PreferenceFragment implements IFragmentBase {
    private MainActivity mMainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);
        SwitchPreference lockAppSetting = (SwitchPreference) findPreference("lockApp");
        lockAppSetting.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Boolean isLock = Boolean.parseBoolean(String.valueOf(newValue));
                Bundle arguments = new Bundle();
                arguments.putBoolean("isLock", isLock);
                Fragment fragment = new LockPatternFragment();
                fragment.setArguments(arguments);
                mMainActivity.pushFragment(fragment);
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
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
