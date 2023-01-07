package com.if5a.rumors;

import retrofit2.Call;
import retrofit2.http.Body;
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
}