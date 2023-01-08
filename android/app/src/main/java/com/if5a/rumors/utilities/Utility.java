package com.if5a.rumors.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utility  {
    private static final String BASE_URL = "https://fcm.googleapis.com/";

    public static Retrofit mRetrofit;

    public static Retrofit getmRetrofit() {
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
