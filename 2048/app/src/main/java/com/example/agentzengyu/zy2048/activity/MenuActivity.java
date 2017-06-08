package com.example.agentzengyu.zy2048.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.adapter.MenuAdapter;
import com.example.agentzengyu.zy2048.app.Config;

public class MenuActivity extends AppCompatActivity {
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;
    private MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initVariable();
        initView();
    }

    private void initVariable() {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new MenuAdapter(this);
        adapter.setItemClickListener(new MenuAdapter.OnRecycleViewItemClickListener() {
            @Override
            public void OnItemClick(View view) {
                Button mbtnMenu = (Button) view.findViewById(R.id.btnMenu);
                int tag = (int) mbtnMenu.getTag();
                choose(tag);
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(4);
    }

    private void choose(int tag) {
        switch (tag) {
            case Config.NEW:

                break;
            case Config.CONTINUE:

                break;
            case Config.RANK:

                break;
            case Config.ABOUT:

                break;
            default:
                break;
        }
    }
}
