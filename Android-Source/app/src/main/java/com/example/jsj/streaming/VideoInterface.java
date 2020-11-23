package com.example.jsj.streaming;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by jsj on 2017-11-01.
 */

public interface VideoInterface {
    @Multipart
    @POST("video.php")
    Call<ResultObject> uploadVideoToServer(@Part MultipartBody.Part video);
}
