package com.example.alfurquan.googlemapgoogleplaces;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URl = "http://bins-env.a9fhp3bw3z.us-east-2.elasticbeanstalk.com/";
    public static Retrofit retrofit = null;

    public static Retrofit getBinsApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
