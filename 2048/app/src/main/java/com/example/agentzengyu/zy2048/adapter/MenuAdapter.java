package com.example.agentzengyu.zy2048.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.activity.AboutActivity;
import com.example.agentzengyu.zy2048.activity.GameActivity;
import com.example.agentzengyu.zy2048.activity.RankActivity;
import com.example.agentzengyu.zy2048.app.Config;

/**
 * Created by Agent ZengYu on 2017/6/8.
 */

/**
 * 菜单页面适配器
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> implements View.OnClickListener {
    private Context context;
    private LayoutInflater inflater;
    private OnRecycleViewItemClickListener listener;
    private Resources resources;
    private int[] icon;
    private String[] title;

    public MenuAdapter(Context context) {
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
        holder.getMivMenu().setImageResource(icon[position % 4]);
        holder.getMbtnMenu().setText(title[position % 4]);
        holder.getMbtnMenu().setTag(position % 4);
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
        icon = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
        title = new String[]{resources.getString(R.string.menu_new), resources.getString(R.string.menu_continue), resources.getString(R.string.menu_rank), resources.getString(R.string.menu_about)};
    }

    /**
     * 菜单页面容器
     */
    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private ImageView mivMenu;
        private Button mbtnMenu;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mivMenu = (ImageView) itemView.findViewById(R.id.ivMenu);
            mbtnMenu = (Button) itemView.findViewById(R.id.btnMenu);
            mbtnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ((int) mbtnMenu.getTag()) {
                        case Config.NEW:
                            Intent intentNew = new Intent(context, GameActivity.class);
                            intentNew.putExtra(Config.MODE,Config.NEW);
                            context.startActivity(intentNew);
                            break;
                        case Config.CONTINUE:
                            Intent intentContinue = new Intent(context, GameActivity.class);
                            intentContinue.putExtra(Config.MODE,Config.CONTINUE);
                            context.startActivity(intentContinue);
                            break;
                        case Config.RANK:
                            Intent intentRank = new Intent(context, RankActivity.class);
                            context.startActivity(intentRank);
                            break;
                        case Config.ABOUT:
                            Intent intentAbout = new Intent(context, AboutActivity.class);
                            context.startActivity(intentAbout);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        public ImageView getMivMenu() {
            return mivMenu;
        }

        public Button getMbtnMenu() {
            return mbtnMenu;
        }
    }
}
