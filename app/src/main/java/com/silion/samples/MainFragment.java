package com.silion.samples;


import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link FragmentBase} subclass.
 */
public class MainFragment extends FragmentBase {
    private View mRootView;
    private ViewGroup mPageBulletLayout;
    private ViewPager mHeaderViewPager;
    private ListView mListView;
    private ListAdapter mListAdapter;
    private GridView mGridView;
    private GridAdapter mGridAdapter;

    private List<View> mHeaderViewList = new ArrayList<>();
    private Map<HeaderType, View> mHeaderTypeViewMap = new HashMap<>();
    private List<MainData> mMainDataList = new ArrayList<>();

    private static String VIEW_BY_LIST = "view_by_list";
    private static String VIEW_BY_GRID = "view_by_grid";
    private SharedPreferences mSharedPreference;
    private Timer mViewPagerScrollTimer;

    protected enum HeaderType {
        ONE, TWO, THREE, FOUR
    }

    protected class MainData {
        protected final Drawable mGridIcon;
        protected final Drawable mListIcon;
        protected final String mTitle;
        protected final String mUri;

        public MainData(int title, String uri) {
            this(R.drawable.home_grid_notice, R.drawable.home_list_others, title, uri);
        }

        public MainData(int gridIcon, int listIcon, int title, String uri) {
            this.mGridIcon = mMainActivity.getResources().getDrawable(gridIcon);
            this.mListIcon = mMainActivity.getResources().getDrawable(listIcon);
            this.mTitle = mMainActivity.getString(title);
            this.mUri = uri;
        }
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTitle = mMainActivity.getString(R.string.app_name);
        setHasOptionsMenu(true);

        mPageBulletLayout = (ViewGroup) mRootView.findViewById(R.id.pageBulleLayout);
        mHeaderViewPager = (ViewPager) mRootView.findViewById(R.id.headerViewPager);
        mHeaderViewPager.setAdapter(mHeaderViewPagerAdapter);
        mHeaderViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updatePageBulletPosition();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        stopAutoScrollViewPager();
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        startAutoScrollViewPager();
                        break;
                    default:
                        break;
                }
            }
        });
        updateHeaderView();

        mListView = (ListView) mRootView.findViewById(R.id.listView);
        mGridView = (GridView) mRootView.findViewById(R.id.gridView);

        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(mMainActivity);
        String viewBy = mSharedPreference.getString("view_by", VIEW_BY_LIST);
        viewBy(viewBy);

        updateMainData();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        startAutoScrollViewPager();
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
                mSharedPreference.edit().putString("view_by", VIEW_BY_LIST).apply();
                viewBy(VIEW_BY_LIST);
                return true;
            case R.id.view_by_grid:
                mSharedPreference.edit().putString("view_by", VIEW_BY_GRID).apply();
                viewBy(VIEW_BY_GRID);
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
                        mPageBulletLayout, false);
                mPageBulletLayout.addView(bullteImageView);
            }
        }

        //Remake & notify headerView if page set is change.
        if (!headerViewList.equals(mHeaderViewList)) {
            mHeaderViewList.clear();
            mHeaderViewList.addAll(headerViewList);

            mHeaderViewPagerAdapter.notifyDataSetChanged();
        }

        // Update bullets
        if (mHeaderViewList.size() > 1) {
            mPageBulletLayout.setVisibility(View.VISIBLE);
            updatePageBulletPosition();
        } else {
            mPageBulletLayout.setVisibility(View.GONE);
        }
    }

    public void updateMainData() {
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
        mMainDataList.add(new MainData(R.string.android_share_weibo, "samples://view/shareWeibo"));
        mMainDataList.add(new MainData(R.string.android_msg_verify, "samples://view/msgVerify"));
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

    public void updatePageBulletPosition() {
        int currentIndex = mHeaderViewPager.getCurrentItem();
        for (int i = 0; i < mPageBulletLayout.getChildCount(); i++) {
            ImageView bulletImage = (ImageView) mPageBulletLayout.getChildAt(i);
            bulletImage.setImageDrawable(mMainActivity.getResources().getDrawable(i == currentIndex
                    ? R.drawable.home_keyvisual_swipe_on : R.drawable.home_keyvisual_swipe_off));
        }
    }

    public void stopAutoScrollViewPager() {
        if (mViewPagerScrollTimer != null) {
            mViewPagerScrollTimer.cancel();
            mViewPagerScrollTimer.purge();
            mViewPagerScrollTimer = null;
        }
    }

    public void startAutoScrollViewPager() {
        if (mViewPagerScrollTimer == null) {
            mViewPagerScrollTimer = new Timer();
            mViewPagerScrollTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            int i = mHeaderViewPager.getCurrentItem() + 1;
                            if (i >= mHeaderViewPagerAdapter.getCount()) {
                                i = 0;
                            }

                            mHeaderViewPager.setCurrentItem(i);
                            if (i == 0) {
                                //TODO changeNormalHeaderImage();
                            }
                        }
                    });
                }
            }, 5000, 5000);

            updateHeaderView();
        }
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

    protected class ListAdapter extends BaseAdapter {

        protected class ViewHolder {
            protected ImageView mIconBackgroundImageView;
            protected ImageView mIconImageView;
            protected TextView mTitleTextView;
        }

        @Override
        public int getCount() {
            return mMainDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mMainDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            View view = convertView;
            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) mMainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.listitem_main, mListView, false);

                viewHolder = new ViewHolder();
                viewHolder.mIconBackgroundImageView = (ImageView) view.findViewById(R.id.iconBackgroundImageView);
                viewHolder.mIconImageView = (ImageView) view.findViewById(R.id.iconImageView);
                viewHolder.mTitleTextView = (TextView) view.findViewById(R.id.title);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            final MainData mainData = (MainData) getItem(position);
            viewHolder.mIconImageView.setImageDrawable(mainData.mListIcon);
            viewHolder.mTitleTextView.setText(mainData.mTitle);
            viewHolder.mIconBackgroundImageView.setColorFilter(mMainActivity.getResources().getColor(R.color.i));

            return view;
        }
    }

    protected class GridAdapter extends BaseAdapter {

        class ViewHolder {
            protected ImageView mIconImageView;
            protected TextView mTitleTextView;
        }

        @Override
        public int getCount() {
            return mMainDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mMainDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            View view = convertView;
            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) mMainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.griditem_main, mGridView, false);

                viewHolder = new ViewHolder();
                viewHolder.mIconImageView = (ImageView) view.findViewById(R.id.iconImageView);
                viewHolder.mTitleTextView = (TextView) view.findViewById(R.id.titleTextView);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            MainData mainData = (MainData) getItem(position);
            viewHolder.mIconImageView.setImageDrawable(mainData.mGridIcon);
            viewHolder.mTitleTextView.setText(mainData.mTitle);
            return view;
        }
    }

    public void viewBy(String viewBy) {
        if (viewBy.equals(VIEW_BY_LIST)) {
            mGridView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mListAdapter = new ListAdapter();
            mListView.setAdapter(mListAdapter);
        } else {
            mListView.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
            mGridAdapter = new GridAdapter();
            mGridView.setAdapter(mGridAdapter);
        }
    }
}
