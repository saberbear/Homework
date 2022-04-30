package com.example.androidhomework;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                EditText editText = findViewById(R.id.edit);
                editText.setText("");
                if (result.getResultCode() == RESULT_OK) {
                    String textFromSecondActivity =
                            result.getData() != null ? result.getData().getStringExtra("SecondActivityText") : null;
                    TextView text = findViewById(R.id.text);
                    if (textFromSecondActivity != null && textFromSecondActivity.length() > 0) {
                        text.setText(textFromSecondActivity);
                    }
                    else {
                        text.setText("页面2没有带回任何内容！");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button button = findViewById(R.id.button);
        EditText editText = findViewById(R.id.edit);
        button.setOnClickListener(view -> {
            String firstActivityText = editText.getText().toString();
            Intent toSecondActivity = new Intent(FirstActivity.this, SecondActivity.class);
            toSecondActivity.putExtra("FirstActivityText", firstActivityText);
            activityResultLauncher.launch(toSecondActivity);
        });
    }
}
