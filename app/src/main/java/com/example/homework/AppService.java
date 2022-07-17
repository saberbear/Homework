package com.example.homework;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppService {

    @GET("get_data.json")
    Call<List<App>> getAppData();

}
