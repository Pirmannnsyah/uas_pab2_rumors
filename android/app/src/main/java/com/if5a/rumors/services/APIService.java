package com.if5a.rumors.services;

import com.if5a.rumors.chats.Sender;
import com.if5a.rumors.chats.ViewData;
import com.if5a.rumors.models.GetJson;
import com.if5a.rumors.models.User;
import com.if5a.rumors.models.UserAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=BA2AqYky9HCJ7Ep3dsVnoXjPyiPVUjpClviWw7ppPe6kTW_3jDhfWa1-hlS3OXV0na4_QI-AjEKygY7f23MDnkM"
            }
    )
    @POST("fcm/send")
    Call<ViewData> sendNotification(@Body Sender body);

    @GET("user/index")
    Call<GetJson<List<UserAPI>>> getUser();

    @FormUrlEncoded
    @POST("user/store")
    Call<GetJson<List<UserAPI>>> postUser(@Field("user_id") String user_id,
                                          @Field("username") String username,
                                          @Field("name") String name);
}