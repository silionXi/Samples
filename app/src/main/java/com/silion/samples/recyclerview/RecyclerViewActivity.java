package com.silion.samples.recyclerview;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.silion.samples.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private List<RecyclerData> mRecyclerDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerAdapter = new RecyclerAdapter(this, mRecyclerDataList);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        //Set RecyclerView's LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        //set RecyclerView's divider
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        initDatas();

        updateAcitonBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recyclerview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.recycler_listView:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                initDatas();
                return true;
            case R.id.recycler_gridView:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                initDatas();
                return true;
            case R.id.recycler_hor_gridView:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
                initDatas();
                return true;
            case R.id.recycler_staggered:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                initStaggeredData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initDatas() {
        mRecyclerDataList.clear();
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view));
        mRecyclerAdapter.notifyDataSetChanged();
    }

    public void initStaggeredData() {
        mRecyclerDataList.clear();
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others, R.string.android_recycler_view, (int) (Math.random() * 300)));
        mRecyclerAdapter.notifyDataSetChanged();
    }

    public void updateAcitonBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
    }
}
