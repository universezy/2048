package com.example.agentzengyu.zy2048.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.adapter.RankAdapter;
import com.example.agentzengyu.zy2048.app.ZY2048Application;
import com.example.agentzengyu.zy2048.entity.Record;

import java.util.ArrayList;

/**
 * 排行活动
 */
public class RankActivity extends AppCompatActivity {
    private ZY2048Application application = null;
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;
    private TextView mtvBest;
    private RankAdapter adapter;
    private ArrayList<Record> records = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        initVariable();
        initView();
        setData();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        application = (ZY2048Application) getApplication();
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new RankAdapter(this, records);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mtvBest = (TextView)findViewById(R.id.tvBest);
        recyclerView = (RecyclerView) findViewById(R.id.rvRank);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);
    }

    /**
     * 设置数据
     */
    private void setData() {
        mtvBest.setText(String.valueOf(application.getService().getBest()));
        records.addAll(application.getService().getRecords());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,R.anim.activity_out);
    }
}
