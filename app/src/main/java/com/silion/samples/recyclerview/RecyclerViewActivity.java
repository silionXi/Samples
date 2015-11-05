package com.silion.samples.recyclerview;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        mRecyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this,
                        "CLICK : " + ((TextView) view.findViewById(R.id.titleTextView)).getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int postition) {
                Toast.makeText(RecyclerViewActivity.this,
                        "LONG CLICK" + ((TextView) view.findViewById(R.id.titleTextView)).getText(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);

        //Set RecyclerView's LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

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
            case R.id.recycler_delete:
                deleteItem(2);
                return true;
            case R.id.recycler_add:
                addItem(2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initDatas() {
        mRecyclerDataList.clear();
        for (int i = 0; i < 20; i++) {
            mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others,
                    getString(R.string.android_recycler_view) + "-" + i));
        }
        mRecyclerAdapter.notifyDataSetChanged();
    }

    public void initStaggeredData() {
        mRecyclerDataList.clear();
        for (int i = 0; i < 20; i++) {
            mRecyclerDataList.add(new RecyclerData(this, R.drawable.home_list_others,
                    getString(R.string.android_recycler_view) + "-" + i, (int) (Math.random() * 300)));
        }
        mRecyclerAdapter.notifyDataSetChanged();
    }

    public void updateAcitonBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
    }

    public void addItem(int position) {
        mRecyclerDataList.add(position, new RecyclerData(this, R.drawable.home_list_others, getString(R.string.app_name)));
//        mRecyclerAdapter.notifyDataSetChanged();
        mRecyclerAdapter.notifyItemInserted(position);
    }

    public void deleteItem(int position) {
        mRecyclerDataList.remove(position);
        mRecyclerAdapter.notifyItemRemoved(position);
    }
}
