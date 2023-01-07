package com.if5a.rumors.chats;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if5a.rumors.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout llReceiver, llSender;
    public TextView tvReceiver, tvSender, tvMessageReceiver, tvMessageSender;
    public ImageView ivReceiver, ivSender, ivMessageReceiver, ivMessageSender;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        llReceiver = itemView.findViewById(R.id.ll_receiver);
        tvReceiver = itemView.findViewById(R.id.tv_receiver);
        tvMessageReceiver = itemView.findViewById(R.id.tv_messege_receiver);
        ivReceiver = itemView.findViewById(R.id.iv_receiver);
        ivMessageReceiver = itemView.findViewById(R.id.iv_messege_receiver);

        llSender = itemView.findViewById(R.id.ll_sender);
        tvSender = itemView.findViewById(R.id.tv_sender);
        tvMessageSender = itemView.findViewById(R.id.tv_messege_sender);
        ivSender = itemView.findViewById(R.id.iv_sender);
        ivMessageSender = itemView.findViewById(R.id.iv_messege_sender);
    }
}
