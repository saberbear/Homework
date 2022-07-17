package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getAppDataButton = findViewById(R.id.get_app_data_button);
        getAppDataButton.setOnClickListener(view -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            AppService appService = retrofit.create(AppService.class);
            appService.getAppData().enqueue(new Callback<List<App>>() {
                @Override
                public void onResponse(Call<List<App>> call, Response<List<App>> response) {
                    List<App> list = response.body();
                    if (list != null) {
                        for (App app : list) {
                            Log.d("MainActivity", "id is " + app.getId());
                            Log.d("MainActivity", "name is " + app.getName());
                            Log.d("MainActivity", "version is " + app.getVersion());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<App>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });
    }
}