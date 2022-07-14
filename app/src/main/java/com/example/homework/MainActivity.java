package com.example.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import com.example.homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int UPDATE_TEXT = 1;

    private TextView textView;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    textView.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textView = binding.textView;

        binding.changeTextButton.setOnClickListener(view -> new Thread(() -> {
            Message msg = new Message();
            msg.what = UPDATE_TEXT;
            handler.sendMessage(msg);
        }).start());
    }
}