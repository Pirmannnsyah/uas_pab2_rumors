package com.if5a.rumors.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.if5a.rumors.R;
import com.if5a.rumors.activities.DetailPostActivity;
import com.if5a.rumors.adapters.ItemPostAdapter;
import com.if5a.rumors.models.GetJson;
import com.if5a.rumors.models.PostAPiIndex;
import com.if5a.rumors.services.APIService;
import com.if5a.rumors.utilities.ItemClickListener;
import com.if5a.rumors.utilities.UtilityAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    private ItemPostAdapter itemPostAdapter;
    private RecyclerView rvNews;
    private ItemClickListener<PostAPiIndex> itemClickListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        rvNews = getView().findViewById(R.id.rv_news);

        UtilityAPI.getRetrofit().create(APIService.class).showPost("news").enqueue(new Callback<GetJson<List<PostAPiIndex>>>() {
            @Override
            public void onResponse(Call<GetJson<List<PostAPiIndex>>> call, Response<GetJson<List<PostAPiIndex>>> response) {
                if(response.isSuccessful()){
                    if (response.body().getData() != null){
                        itemClickListener = new ItemClickListener<PostAPiIndex>() {
                            @Override
                            public void itemClick(PostAPiIndex data, int position) {
                                Intent intent = new Intent(getActivity(), DetailPostActivity.class);
                                intent.putExtra("data", data);
                                startActivity(intent);
                            }
                        };
                        itemPostAdapter = new ItemPostAdapter(itemClickListener);
                        itemPostAdapter.setItemPostAdapter(response.body());
                        rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvNews.setAdapter(itemPostAdapter);
                    }
                } else {
                    Log.d("TAG", "onResponse ===============================================: GAGAL");

                }
            }

            @Override
            public void onFailure(Call<GetJson<List<PostAPiIndex>>> call, Throwable t) {

            }
        });

    }
}