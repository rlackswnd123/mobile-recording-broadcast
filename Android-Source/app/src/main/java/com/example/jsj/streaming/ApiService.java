package com.example.jsj.streaming;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jsj on 2017-10-26.
 */

public interface ApiService {

    @POST("register")
    Call<ResponseBody>getPostComment(@Query("id") String id);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody>getPostCommentstr(@Field("id") String id,@Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody>getPostLogin(@Field("id")String id,@Field("password")String password);

    @GET("category/club")
    Call<ResponseBody>getname();
    @GET("category/university")
    Call<ResponseBody>getuni();
    @GET("category/department")
    Call<ResponseBody>getdepar();
    @GET("image/")
    Call<ResponseBody>getvideo();


}
