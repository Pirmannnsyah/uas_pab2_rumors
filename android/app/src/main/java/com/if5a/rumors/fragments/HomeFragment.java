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
import com.if5a.rumors.models.UserAPI;
import com.if5a.rumors.services.APIService;
import com.if5a.rumors.utilities.ItemClickListener;
import com.if5a.rumors.utilities.UtilityAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ItemPostAdapter itemPostAdapter;
    private RecyclerView rvHome;
    private ItemClickListener<PostAPiIndex> itemClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        rvHome = getView().findViewById(R.id.rv_home);

        UtilityAPI.getRetrofit().create(APIService.class).getPost().enqueue(new Callback<GetJson<List<PostAPiIndex>>>() {
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
                        rvHome.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvHome.setAdapter(itemPostAdapter);
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