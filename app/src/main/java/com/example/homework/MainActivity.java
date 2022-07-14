package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.DownloadBinder downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startServiceButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        });

        binding.stopServiceButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        });

        binding.bindServiceButton.setOnClickListener(view -> {
            Intent bindIntent = new Intent(this, MyService.class);
            bindService(bindIntent, connection, BIND_AUTO_CREATE);
        });

        binding.unbindServiceButton.setOnClickListener(view -> unbindService(connection));

        binding.startIntentServiceButton.setOnClickListener(view -> {
            Log.d("MainActivity", "Thread id is " + Thread.currentThread().getName());
            Intent intent = new Intent(this, MyIntentService.class);
            startService(intent);
        });
    }
}