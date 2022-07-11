package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import com.example.homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel("normal", "Normal", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel1);
            NotificationChannel channel2 = new NotificationChannel("important", "Important", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel2);
        }

        binding.sendNotice.setOnClickListener(view -> {
            Intent intent = new Intent(this, NotificationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            Notification notification = new NotificationCompat.Builder(this, "normal")
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(
                            "社会主义核心价值观：" +
                            "富强，民主，文明，和谐，" +
                            "自由，平等，公正，法制，" +
                            "爱国，敬业，诚信，友善。" +
                            "武警部队两个维护：" +
                            "维护国家安全和社会稳定、保卫人民美好生活，维护政治安全特别是政权安全、制度安全。"))
                    .build();
            manager.notify(1, notification);
        });

        binding.sendImportantNotice.setOnClickListener(view -> {
            Intent intent = new Intent(this, NotificationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            Notification notification = new NotificationCompat.Builder(this, "important")
                    .setContentTitle("This is important content title")
                    .setContentText("This is important content text")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            manager.notify(1, notification);
        });
    }
}