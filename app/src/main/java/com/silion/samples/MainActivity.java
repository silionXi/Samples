package com.silion.samples;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = mFragmentManager.findFragmentById(R.id.container);
                if (fragment instanceof IFragmentBase) {
                    IFragmentBase fragmentBase = (IFragmentBase) fragment;
                    fragmentBase.updataActionBar();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        });
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = new MainFragment();
        String tag = fragment.getClass().getSimpleName();
        fragmentTransaction.add(R.id.container, fragment, tag);
        fragmentTransaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.container);
        if (!(currentFragment != null && currentFragment instanceof LockPatternFragment)) {
            fragmentTransaction.add(R.id.container, new LockPatternFragment());
            fragmentTransaction.addToBackStack(null);
            if (currentFragment != null) {
                fragmentTransaction.hide(currentFragment);
            }
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            IFragmentBase fragment = (IFragmentBase) mFragmentManager.findFragmentById(R.id.container);
            fragment.onBackPressed();
        } else {
            finish();
        }
    }

    public void performActionLink(String actionLink) {
        if (actionLink == null || actionLink.length() == 0) {
            return;
        }

        Log.d(TAG, "performActionLink, actionLink = " + actionLink);

        URI uri = null;
        try {
            uri = new URI(actionLink.trim());
        } catch (URISyntaxException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        if (uri == null) {
            return;
        }

        String scheme = uri.getScheme() != null ? uri.getScheme() : "";
        String function = uri.getHost() != null ? uri.getHost() : "";
        String path = uri.getPath() != null ? uri.getPath() : "";
        if (path.length() > 1) {
            path = path.substring(1);
        }

        List<NameValuePair> params = URLEncodedUtils.parse(uri, "UTF-8");

        switch (scheme) {
            case "samples": {
                switch (function) {
                    case "view": {
                        switch (path) {
                            case "main": {
                                Bundle arguments = new Bundle();
                                FragmentBase fragment = new MainFragment();
                                fragment.setArguments(arguments);
                                pushFragment(fragment);
                                break;
                            }
                            default:
                                break;
                        }
                        break;
                    }
                    case "activity": {
                        break;
                    }
                    default:
                        break;
                }
                break;
            }
            // Jump to SMS app
            case "smsto": {
                break;
            }
            // Jump to dial app
            case "tel": {
                break;
            }
            // Jump to dial app and call directly
            case "call": {
                break;
            }
            default:
                break;
        }
    }

    public void pushFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        String tag = fragment.getClass().getSimpleName();
        fragmentTransaction.add(R.id.container, fragment, tag);
        fragmentTransaction.addToBackStack(tag);

        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.container);
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        fragmentTransaction.commitAllowingStateLoss();
        // to avoid multi onclick to add multi fragment
        //mFragmentManager.executePendingTransactions();
    }

    public void popFragment() {
        mFragmentManager.popBackStackImmediate();
    }
}
