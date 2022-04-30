package com.example.androidfirsthomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent fromFirstActivity = getIntent();
        String textFromFirstActivity = fromFirstActivity.getStringExtra("FirstActivityText");
        TextView text = findViewById(R.id.text);
        if (textFromFirstActivity != null && textFromFirstActivity.length() > 0) {
            text.setText(textFromFirstActivity);
        }
        else {
            Toast.makeText(SecondActivity.this,
                    "您输入内容为空，无法跳转下一个页面！",
                    Toast.LENGTH_SHORT).show();
        }
        Button button = findViewById(R.id.button);
        EditText editText = findViewById(R.id.edit);
        button.setOnClickListener(view -> {
            String secondActivityText = editText.getText().toString();
            Intent backToFirstActivity = new Intent();
            backToFirstActivity.putExtra("SecondActivityText", secondActivityText);
            setResult(RESULT_OK, backToFirstActivity);
            finish();
        });
    }
}