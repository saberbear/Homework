package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.homework.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sendRequestButton.setOnClickListener(view -> sendRequestWithOkHttp());
    }

    private void sendRequestWithOkHttp() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                showResponse(responseData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(() -> binding.responseText.setText(response));
    }

}