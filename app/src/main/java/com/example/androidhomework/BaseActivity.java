package com.example.androidhomework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected static final String FORCE_QUIT_STRING_EXTRA = "force_quit";

    protected static final String FORCE_QUIT_BROADCAST_INTENT = "com.example.androidhomework.FORCE_QUIT";

    private ForceQuitReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FORCE_QUIT_BROADCAST_INTENT);
        receiver = new ForceQuitReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        receiver = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    static class ForceQuitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ActivityCollector.finishAll();
            Intent intent1 = new Intent(context, LoginActivity.class);
            intent1.putExtra(FORCE_QUIT_STRING_EXTRA, "强制退出，请重新登录");
            context.startActivity(intent1);
        }
    }
}
