package com.example.alfurquan.googlemapgoogleplaces;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface FcmServices {
    @GET
    Call<ResponseBody1> getNotifications(@Url String url);
}
