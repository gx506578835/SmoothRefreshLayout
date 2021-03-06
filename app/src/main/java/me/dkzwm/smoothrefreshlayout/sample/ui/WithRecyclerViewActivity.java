package me.dkzwm.smoothrefreshlayout.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.List;

import me.dkzwm.smoothrefreshlayout.MaterialSmoothRefreshLayout;
import me.dkzwm.smoothrefreshlayout.RefreshingListenerAdapter;
import me.dkzwm.smoothrefreshlayout.SmoothRefreshLayout;
import me.dkzwm.smoothrefreshlayout.indicator.IIndicator;
import me.dkzwm.smoothrefreshlayout.sample.R;
import me.dkzwm.smoothrefreshlayout.sample.adapter.RecyclerViewAdapter;
import me.dkzwm.smoothrefreshlayout.sample.util.DataUtil;

/**
 * Created by dkzwm on 2017/6/1.
 *
 * @author dkzwm
 */
public class WithRecyclerViewActivity extends AppCompatActivity {
    private MaterialSmoothRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private Handler mHandler = new Handler();
    private int mCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_recyclerview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.with_recyclerView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_with_recyclerView_activity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(getLayoutInflater());
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout = (MaterialSmoothRefreshLayout) findViewById(R.id.smoothRefreshLayout_with_recyclerView_activity);
        mRefreshLayout.materialStyle();
        mRefreshLayout.setEnableScrollToBottomAutoLoadMore(true);
        mRefreshLayout.setOnRefreshListener(new RefreshingListenerAdapter() {
            @Override
            public void onRefreshBegin(final boolean isRefresh) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefresh) {
                            mCount = 0;
                            List<String> list = DataUtil.createList(mCount, 20);
                            mCount += 20;
                            mAdapter.updateData(list);
                        } else {
                            List<String> list = DataUtil.createList(mCount, 20);
                            mCount += 20;
                            mAdapter.appendData(list);
                        }
                        mRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }
        });
        mRefreshLayout.autoRefresh(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WithRecyclerViewActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
