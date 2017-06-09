package com.example.agentzengyu.zy2048.app;

import android.app.Activity;
import android.app.Application;

import com.example.agentzengyu.zy2048.activity.MenuActivity;
import com.example.agentzengyu.zy2048.service.ZY2048Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agent ZengYu on 2017/6/9.
 */

public class ZY2048Application extends Application {
    private List<Activity> activities = new ArrayList<>();
    private ZY2048Service service = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 加入一个活动到列表
     *
     * @param activity 活动实例
     */
    public void addActivity(Activity activity) {
        if (!activities.contains(activity))
            activities.add(activity);
    }

    /**
     * 从列表销毁一个活动
     *
     * @param activity 活动实例
     */
    public void destroyActivity(Activity activity) {
        if (activity == null)
            return;
        if (activities.contains(activity)) {
            activities.remove(activity);
            activity.finish();
        }
    }

    /**
     * 销毁所有活动
     */
    public void destroyAllActivities() {
        for (Activity activity : activities) {
            if (activity != null && !(activity instanceof MenuActivity))
                destroyActivity(activity);
        }
        if (activities.size() == 1 && activities.get(0) instanceof MenuActivity)
            destroyActivity(activities.get(0));
        activities.clear();
    }

    /**
     * 绑定服务
     *
     * @param service
     */
    public void setService(ZY2048Service service) {
        this.service = service;
    }

    /**
     * 获取服务
     *
     * @return
     */
    public ZY2048Service getService() {
        return this.service;
    }

}
