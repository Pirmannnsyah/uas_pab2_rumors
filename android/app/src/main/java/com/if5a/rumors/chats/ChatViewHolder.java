package com.if5a.rumors.chats;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if5a.rumors.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public TextView tvMessenger, tvMessege;
    public ImageView ivMessenger, ivMessage;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        tvMessenger = itemView.findViewById(R.id.tv_messenger);
        tvMessege = itemView.findViewById(R.id.tv_messege);
        ivMessenger = itemView.findViewById(R.id.iv_messenger);
        ivMessage = itemView.findViewById(R.id.iv_messege);
    }
}
