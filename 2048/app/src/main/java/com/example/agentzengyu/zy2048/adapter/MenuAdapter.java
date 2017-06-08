package com.example.agentzengyu.zy2048.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.agentzengyu.zy2048.R;

/**
 * Created by Agent ZengYu on 2017/6/8.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> implements View.OnClickListener {
    private LayoutInflater inflater;
    private OnRecycleViewItemClickListener listener;
    private Resources resources;
    private int[] icon;
    private String[] title;

    public MenuAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        resources = context.getResources();
        setData();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_menu, parent, false);
        MenuViewHolder holder = new MenuViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.getMivMenu().setImageResource(icon[position / 4]);
        holder.getMbtnMenu().setText(title[position / 4]);
        holder.getMbtnMenu().setTag(position / 4);
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

    public void setItemClickListener(OnRecycleViewItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecycleViewItemClickListener {
        void OnItemClick(View view);
    }

    public void setData(){
        icon = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
        title = new String[]{resources.getString(R.string.title_new), resources.getString(R.string.title_continue), resources.getString(R.string.title_rank), resources.getString(R.string.title_about)};
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private ImageView mivMenu;
        private Button mbtnMenu;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mivMenu = (ImageView) itemView.findViewById(R.id.ivMenu);
            mbtnMenu = (Button) itemView.findViewById(R.id.btnMenu);
        }

        public ImageView getMivMenu() {
            return mivMenu;
        }

        public Button getMbtnMenu() {
            return mbtnMenu;
        }

        public void setMivMenu(ImageView mivMenu) {
            this.mivMenu = mivMenu;
        }

        public void setMbtnMenu(Button mbtnMenu) {
            this.mbtnMenu = mbtnMenu;
        }
    }
}
