package com.example.agentzengyu.zy2048.adapter;

/**
 * Created by Agent ZengYu on 2017/6/13.
 */

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agentzengyu.zy2048.R;

/**
 * 消息适配器
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private Resources resources;
    private String[] message;

    public MessageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        resources = context.getResources();
        setData();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_message, parent, false);
        MessageViewHolder holder = new MessageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.getMtvMessage().setText(message[position % message.length]);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 设置数据
     */
    public void setData() {
        message = new String[]{
                resources.getString(R.string.message_rule),
                resources.getString(R.string.message_test),
                resources.getString(R.string.message_tips),
                resources.getString(R.string.message_test)};
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView mtvMessage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mtvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
        }

        public TextView getMtvMessage() {
            return mtvMessage;
        }
    }
}
