package com.example.alfurquan.googlemapgoogleplaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DeleteBinsInterface {
    @GET
    Call<List<Bins>> getBins(@Url String url);
}
