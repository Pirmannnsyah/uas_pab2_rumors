package com.if5a.rumors.services;

import com.if5a.rumors.chats.Sender;
import com.if5a.rumors.chats.ViewData;
import com.if5a.rumors.models.GetJson;
import com.if5a.rumors.models.LikeAPI;
import com.if5a.rumors.models.PostAPI;
import com.if5a.rumors.models.PostAPiIndex;
import com.if5a.rumors.models.User;
import com.if5a.rumors.models.UserAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Call<GetJson<UserAPI>> getUser();

    @FormUrlEncoded
    @POST("user/store")
    Call<GetJson<UserAPI>> postUser(@Field("user_id") String user_id,
                                          @Field("username") String username,
                                          @Field("name") String name);

    @FormUrlEncoded
    @POST("user/update/{id}")
    Call<GetJson<UserAPI>> updateUser(
            @Path("id")String id,
            @Field("name") String name);

    @FormUrlEncoded
    @GET("user/destroy/{id}")
    Call<GetJson<UserAPI>> deleteUser(
            @Path("id")String id);

    @GET("post/index")
    Call<GetJson<List<PostAPiIndex>>> getPost();

    @FormUrlEncoded
    @POST("post/store")
    Call<GetJson<PostAPI>> postPost(@Field("user_id") String user_id,
                                          @Field("kategori") String kategori,
                                          @Field("judul") String judul,
                                          @Field("content")String content);

    @FormUrlEncoded
    @POST("post/update/{id}")
    Call<GetJson<PostAPI>> updatePost(
            @Path("id")String id,
            @Field("kategori") String kategori,
            @Field("judul") String judul,
            @Field("content")String content);

    @GET("post/destroy/{id}")
    Call<GetJson<PostAPI>> deletePost(
            @Path("id")String id);

    @GET("post/show/{kategori}")
    Call<GetJson<List<PostAPiIndex>>> showPost(
            @Path("kategori")String kategori);

}