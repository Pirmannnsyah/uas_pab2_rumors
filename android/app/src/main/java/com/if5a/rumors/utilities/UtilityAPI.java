package com.if5a.rumors.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilityAPI {

    public static final String RUMORS_BASE_URL = "https://pirmansyahh.000webhostapp.com/api/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new  Retrofit.Builder()
                    .baseUrl(RUMORS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
