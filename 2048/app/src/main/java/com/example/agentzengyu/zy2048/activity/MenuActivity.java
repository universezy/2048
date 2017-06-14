package com.example.agentzengyu.zy2048.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.adapter.MenuAdapter;
import com.example.agentzengyu.zy2048.app.ZY2048Application;
import com.example.agentzengyu.zy2048.service.ZY2048Service;

/**
 * 菜单页面
 */
public class MenuActivity extends AppCompatActivity {
    private ZY2048Application application = null;
    protected ZY2048Service.ServiceBinder binder;
    private ServiceConnection connection;
    private LinearLayoutManager manager;
    private MenuAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initVariable();
        initView();
        setService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.welcome_out);
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        application = (ZY2048Application) getApplication();
        application.addActivity(this);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new MenuAdapter(this);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvMenu);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(1024);
    }

    /**
     * 设置服务
     */
    public void setService() {
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (ZY2048Service.ServiceBinder) service;  //获取其实例
                Log.e("Service", "Service has started.");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        Intent intent = new Intent(MenuActivity.this, ZY2048Service.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
}
