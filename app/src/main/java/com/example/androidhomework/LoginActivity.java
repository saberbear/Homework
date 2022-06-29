package com.example.androidhomework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.androidhomework.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 强制退出提醒
        String forceQuit = getIntent().getStringExtra(FORCE_QUIT_STRING_EXTRA);
        if (forceQuit != null) {
            Toast.makeText(LoginActivity.this, forceQuit, Toast.LENGTH_SHORT).show();
        }
        // 记住密码
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            binding.account.setText(account);
            binding.password.setText(password);
            binding.rememberPass.setChecked(true);
        }

        binding.login.setOnClickListener(view -> {

            String account = binding.account.getText().toString();
            String password = binding.password.getText().toString();

            if (account.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "输入账号/密码为空，登录失败", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor editor = pref.edit();
                if (binding.rememberPass.isChecked()) { // 是否记住密码
                    editor.putBoolean("remember_password", true);
                    editor.putString("account", account);
                    editor.putString("password", password);
                } else {
                    editor.clear();
                }
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, ClassMemberActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}