package com.example.agentzengyu.zy2048.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.adapter.RankAdapter;
import com.example.agentzengyu.zy2048.entity.Record;

import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;
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

    private void initVariable() {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new RankAdapter(this, records);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvRank);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);
    }

    private void setData() {

    }
}
