package com.example.alfurquan.googlemapgoogleplaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface BinsDetailsInterface {
    @POST("ShowD")
    Call<Bins> getBins(@Body String json);
}
