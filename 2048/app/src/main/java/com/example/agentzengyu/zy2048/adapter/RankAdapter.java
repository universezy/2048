package com.example.agentzengyu.zy2048.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.entity.Record;

import java.util.ArrayList;

/**
 * Created by Agent ZengYu on 2017/6/9.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Record> records;

    public RankAdapter(Context context,ArrayList<Record> records) {
        this.context = context;
        this.records = records;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_record, parent, false);
        RankViewHolder holder = new RankViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RankViewHolder holder, int position) {
        holder.getMtvRank().setText("" + records.get(position).getRank());
        holder.getMtvName().setText("" + records.get(position).getName());
        holder.getMtvScore().setText("" + records.get(position).getScore());
        holder.getMtvTime().setText("" + records.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class RankViewHolder extends RecyclerView.ViewHolder {
        private TextView mtvRank, mtvName, mtvScore, mtvTime;

        public RankViewHolder(View itemView) {
            super(itemView);

            mtvRank = (TextView) itemView.findViewById(R.id.tvRank);
            mtvName = (TextView) itemView.findViewById(R.id.tvName);
            mtvScore = (TextView) itemView.findViewById(R.id.tvScore);
            mtvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }

        public TextView getMtvRank() {
            return mtvRank;
        }

        public TextView getMtvName() {
            return mtvName;
        }

        public TextView getMtvScore() {
            return mtvScore;
        }

        public TextView getMtvTime() {
            return mtvTime;
        }
    }
}
