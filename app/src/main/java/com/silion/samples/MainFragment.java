package com.silion.samples;


import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link FragmentBase} subclass.
 */
public class MainFragment extends FragmentBase {
    private View mRootView;
    private ViewGroup mPageBulletLayout;
    private ViewPager mHeaderViewPager;

    private List<View> mHeaderViewList = new ArrayList<>();
    private Map<HeaderType, View> mHeaderTypeViewMap = new HashMap<>();

    protected enum HeaderType {
        ONE, TWO, THREE, FOUR
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTitle = mMainActivity.getString(R.string.app_name);
        setHasOptionsMenu(true);

        mPageBulletLayout = (ViewGroup) mRootView.findViewById(R.id.pageBulleLayout);
        mHeaderViewPager = (ViewPager) mRootView.findViewById(R.id.headerViewPager);
        mHeaderViewPager.setAdapter(mHeaderViewPagerAdapter);
        updateHeaderView();
        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_by_list:
                return true;
            case R.id.view_by_grid:
                return true;
            case R.id.action_settings:
                performActionLink("samples://view/setting");
                return true;
            case R.id.developer:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updataActionBar() {
        ActionBar actionBar = mMainActivity.getActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        super.updataActionBar();
    }

    public void updateHeaderView() {
        List<View> headerViewList = new ArrayList<>();
        View oneHeaderView = mHeaderTypeViewMap.get(HeaderType.ONE);
        if (oneHeaderView == null) {
            oneHeaderView = createHeaderView(HeaderType.ONE);
            mHeaderTypeViewMap.put(HeaderType.ONE, oneHeaderView);
        }
        if (oneHeaderView != null) {
            headerViewList.add(oneHeaderView);
        }

        View twoHeaderView = mHeaderTypeViewMap.get(HeaderType.TWO);
        if (twoHeaderView == null) {
            twoHeaderView = createHeaderView(HeaderType.TWO);
            mHeaderTypeViewMap.put(HeaderType.TWO, twoHeaderView);
        }
        if (twoHeaderView != null) {
            headerViewList.add(twoHeaderView);
        }

        //Remake bullets if page count is changed.
        if (headerViewList.size() != mHeaderViewList.size()) {
            mPageBulletLayout.removeAllViews();
            LayoutInflater layoutInflater = (LayoutInflater) mMainActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < headerViewList.size(); i++) {
                ImageView bullteImageView = (ImageView) layoutInflater.inflate(R.layout.view_header_view_bullet,
                        mPageBulletLayout,false);
                mPageBulletLayout.addView(bullteImageView);
            }
        }

        //Remake & notify headerView if page set is change.
        if (!headerViewList.equals(mHeaderViewList)) {
            mHeaderViewList.clear();;
            mHeaderViewList.addAll(headerViewList);

            mHeaderViewPagerAdapter.notifyDataSetChanged();
        }
    }

    public View createHeaderView(HeaderType type) {
        LayoutInflater layoutInflater = (LayoutInflater) mMainActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = null;
        switch (type) {
            case ONE: {
                headerView = layoutInflater.inflate(R.layout.view_main_header_one, mHeaderViewPager, false);
                final TextView titleTextView = (TextView) headerView.findViewById(R.id.title);
                titleTextView.setText(String.format(mMainActivity.getString(R.string.string_format), 0));
                Button changeButton = (Button) headerView.findViewById(R.id.change);
                changeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        titleTextView.setText(String.format(mMainActivity.getString(R.string.string_format), 9));
                    }
                });
                break;
            }
            case TWO: {
                headerView = layoutInflater.inflate(R.layout.view_main_header_two, mHeaderViewPager, false);
                break;
            }
            default:
                break;
        }
        return headerView;
    }

    protected final PagerAdapter mHeaderViewPagerAdapter = new PagerAdapter() {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mHeaderViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mHeaderViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o; //check if two objects are same
        }
    };
}
