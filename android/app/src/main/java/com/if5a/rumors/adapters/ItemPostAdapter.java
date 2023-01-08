package com.if5a.rumors.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.if5a.rumors.R;
import com.if5a.rumors.models.GetJson;
import com.if5a.rumors.models.PostAPiIndex;
import com.if5a.rumors.utilities.ItemClickListener;

import java.util.List;

public class ItemPostAdapter extends RecyclerView.Adapter<ItemPostAdapter.ListViewHolder> {
    private GetJson<List<PostAPiIndex>> postAPi;
    private ItemClickListener<PostAPiIndex> itemClickListener;

    public ItemPostAdapter(ItemClickListener<PostAPiIndex> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemPostAdapter(GetJson<List<PostAPiIndex>> postAPi){
        this.postAPi = postAPi;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemPostAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPostAdapter.ListViewHolder holder, int position) {
        PostAPiIndex result = postAPi.getData().get(position);
        holder.tvNamePost.setText(result.getUsername());
        holder.tvDatePost.setText(result.getDate());
        holder.tvJudulPost.setText(result.getJudul());
        holder.tvDeskripsiPost.setText(result.getContent());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.itemClick(postAPi.getData().get(position), position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return postAPi.getData().size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPost;
        TextView tvNamePost, tvDatePost, tvJudulPost, tvDeskripsiPost;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPost = itemView.findViewById(R.id.iv_detail_profil_post);
            tvNamePost = itemView.findViewById(R.id.tv_detail_username_post);
            tvDatePost = itemView.findViewById(R.id.tv_detail_date_post);
            tvJudulPost = itemView.findViewById(R.id.tv_detail_judul_post);
            tvDeskripsiPost = itemView.findViewById(R.id.tv_deskripsi_post);

        }
    }
}
