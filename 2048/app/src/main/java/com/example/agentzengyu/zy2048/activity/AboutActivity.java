package com.example.agentzengyu.zy2048.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.adapter.AboutAdapter;

public class AboutActivity extends AppCompatActivity {
    private LinearLayoutManager manager;
    private AboutAdapter adapter;
    private Handler handler;
    private Runnable runnable;

    private RecyclerView recyclerView;

    private int index = 0;
    private boolean run = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initVariable();
        initView();
        startAutoShow();
    }

    private void initVariable() {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new AboutAdapter(this);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (run) {
                    recyclerView.scrollToPosition(index + 1);
                    index++;
                }
                startAutoShow();
            }
        };
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvAbout);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(4);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        run = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        run = true;
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void startAutoShow() {
            handler.postDelayed(runnable, 1000);
    }
}
