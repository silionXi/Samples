package com.silion.samples;


import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link FragmentBase} subclass.
 */
public class LockPatternFragment extends FragmentBase implements LockPatternView.OnPatternChangeListener {
    private View mRootView = null;
    private LockPatternView mLockPatternView;
    private TextView mTitilTextView;
    private Button mSavePwButton;

    private SharedPreferences mPreferences;
    private String mPassword;
    private boolean mIsLock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_lock_pattern, container, false);
        mLockPatternView = (LockPatternView) mRootView.findViewById(R.id.lockPatternView);
        mLockPatternView.setOnPatternChangeListener(this);
        mTitilTextView = (TextView) mRootView.findViewById(R.id.title);
        mSavePwButton = (Button) mRootView.findViewById(R.id.savePassword);
        mSavePwButton.setVisibility(View.GONE);
        Bundle arguments = this.getArguments();
        if (arguments != null) {
            mIsLock = arguments.getBoolean("isLock", false);
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        mPreferences = mMainActivity.getSharedPreferences("lockPatternDemo", Context.MODE_PRIVATE);
        mPassword = mPreferences.getString("password", null);
        if (mIsLock || mPassword == null || mPassword.length() < 4) {
            mTitilTextView.setText(getResources().getString(R.string.new_password));
        } else {
            mTitilTextView.setText(getResources().getString(R.string.input_password));
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mMainActivity != null) {
            mMainActivity.finish();
        }
    }

    @Override
    public void updataActionBar() {
        ActionBar actionBar = mMainActivity.getActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.hide();
    }

    @Override
    public void onPatternChange(final String pattern) {
        if (mIsLock || mPassword == null || mPassword.length() < 4) {
            if (pattern == null) {
                Toast.makeText(mMainActivity, getString(R.string.password_too_short), Toast.LENGTH_SHORT).show();
            } else {
                mSavePwButton.setVisibility(View.VISIBLE);
                mSavePwButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPreferences.edit().putString("password", pattern).apply();
                        if (mMainActivity != null) {
                            mMainActivity.getFragmentManager().popBackStack();
                        }
                    }
                });
            }
        } else {
            if (mPassword.equals(pattern)) {
                if (mMainActivity != null) {
                    mMainActivity.getFragmentManager().popBackStack();
                }
            } else {
                Toast.makeText(mMainActivity, getString(R.string.password_error), Toast.LENGTH_SHORT).show();
            }
        }
    }


}
