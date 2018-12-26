package com.example.alfurquan.googlemapgoogleplaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CreateBinsInterface {

    @POST("Add")
    Call<Bins> createBins(@Body String json);
}
