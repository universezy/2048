package com.example.agentzengyu.zy2048.adapter;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.activity.AboutActivity;
import com.example.agentzengyu.zy2048.activity.GameActivity;
import com.example.agentzengyu.zy2048.activity.MenuActivity;
import com.example.agentzengyu.zy2048.activity.RankActivity;
import com.example.agentzengyu.zy2048.app.Config;
import com.example.agentzengyu.zy2048.view.CircleImageView;

/**
 * Created by Agent ZengYu on 2017/6/8.
 */

/**
 * 菜单页面适配器
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> implements View.OnClickListener {
    private MenuActivity context;
    private LayoutInflater inflater;
    private OnRecycleViewItemClickListener listener;
    private Resources resources;
    private int[] icon;
    private String[] title;

    public MenuAdapter(MenuActivity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        resources = context.getResources();
        setData();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_menu, parent, false);
        MenuViewHolder holder = new MenuViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.getMcivMenu().setImageResource(icon[position % icon.length]);
        holder.getMbtnMenu().setText(title[position % title.length]);
        holder.getMbtnMenu().setTag(position % title.length);
    }

    @Override
    public void onViewAttachedToWindow(MenuViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        View view = holder.itemView;
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_menu_in);
        view.startAnimation(animation);
    }

    @Override
    public void onViewDetachedFromWindow(MenuViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        View view = holder.itemView;
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_menu_out);
        view.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.OnItemClick(v);
    }

    /**
     * item点击监听
     *
     * @param listener
     */
    public void setItemClickListener(OnRecycleViewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * item点击监听接口
     */
    public interface OnRecycleViewItemClickListener {
        void OnItemClick(View view);
    }

    /**
     * 设置数据
     */
    public void setData() {
        icon = new int[]{R.mipmap.big_new, R.mipmap.big_continue, R.mipmap.big_rank, R.mipmap.big_about};
        title = new String[]{resources.getString(R.string.menu_new), resources.getString(R.string.menu_continue), resources.getString(R.string.menu_rank), resources.getString(R.string.menu_about)};
    }

    /**
     * 菜单页面容器
     */
    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mcivMenu;
        private Button mbtnMenu;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mcivMenu = (CircleImageView) itemView.findViewById(R.id.civMenu);
            mbtnMenu = (Button) itemView.findViewById(R.id.btnMenu);
            mbtnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ((int) mbtnMenu.getTag()) {
                        case Config.NEW:
                            Intent intentNew = new Intent(context, GameActivity.class);
                            intentNew.putExtra(Config.MODE, Config.NEW);
                            context.startActivity(intentNew);
                            context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            break;
                        case Config.CONTINUE:
                            Intent intentContinue = new Intent(context, GameActivity.class);
                            intentContinue.putExtra(Config.MODE, Config.CONTINUE);
                            context.startActivity(intentContinue);
                            context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            break;
                        case Config.RANK:
                            Intent intentRank = new Intent(context, RankActivity.class);
                            context.startActivity(intentRank);
                            context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            break;
                        case Config.ABOUT:
                            Intent intentAbout = new Intent(context, AboutActivity.class);
                            context.startActivity(intentAbout);
                            context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        public CircleImageView getMcivMenu() {
            return mcivMenu;
        }

        public Button getMbtnMenu() {
            return mbtnMenu;
        }
    }
}
