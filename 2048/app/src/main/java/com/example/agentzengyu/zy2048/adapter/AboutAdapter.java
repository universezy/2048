package com.example.agentzengyu.zy2048.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.activity.AboutActivity;

/**
 * Created by Agent ZengYu on 2017/6/9.
 */

/**
 * 关于页面适配器
 */
public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutViewHolder>  {
    private AboutActivity context;
    private LayoutInflater inflater;
    private Resources resources;
    private int[] icon;
    private String[] title;

    public AboutAdapter(AboutActivity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        resources = context.getResources();
        setData();
    }

    @Override
    public AboutAdapter.AboutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_about, parent, false);
        AboutViewHolder holder = new AboutViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AboutAdapter.AboutViewHolder holder, int position) {
        holder.getMtvAbout().setBackgroundResource(icon[position % 4]);
        holder.getMtvAbout().setText(title[position % 4]);
    }

    @Override
    public void onViewAttachedToWindow(AboutViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        View view = holder.itemView;
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_about_in);
        view.startAnimation(animation);
    }

    @Override
    public void onViewDetachedFromWindow(AboutViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        View view = holder.itemView;
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_about_out);
        view.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 设置数据
     */
    public void setData() {
        icon = new int[]{R.mipmap.uestc, R.mipmap.logo, R.mipmap.csdn, R.mipmap.github};
        title = new String[]{resources.getString(R.string.about_author), resources.getString(R.string.about_copyright), resources.getString(R.string.about_blog), resources.getString(R.string.about_github)};
    }

    /**
     * 关于页面布局容器
     */
    public class AboutViewHolder extends RecyclerView.ViewHolder {
        private TextView mtvAbout;

        public AboutViewHolder(View itemView) {
            super(itemView);
            mtvAbout = (TextView) itemView.findViewById(R.id.tvAbout);
        }

        public TextView getMtvAbout() {
            return mtvAbout;
        }
    }
}
