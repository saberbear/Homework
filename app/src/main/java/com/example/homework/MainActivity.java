package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.example.homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private final MediaPlayer mediaPlayer = new MediaPlayer();

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        videoView = binding.videoView;

        playMedia();
        playVideo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        videoView.suspend();
    }

    private void initMediaPlayer() {
        AssetManager assetManager = getAssets();
        try {
            AssetFileDescriptor fd = assetManager.openFd("music.mp3");
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playMedia() {
        initMediaPlayer();

        binding.mediaPlay.setOnClickListener(view -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        });

        binding.mediaPause.setOnClickListener(view -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        });

        binding.mediaStop.setOnClickListener(view -> {
            mediaPlayer.reset();
            initMediaPlayer();
        });
    }

    private void playVideo() {
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(uri);

        binding.videoPlay.setOnClickListener(view -> {
            if (!videoView.isPlaying()) {
                videoView.start();
            }
        });

        binding.videoPause.setOnClickListener(view -> {
            if (videoView.isPlaying()) {
                videoView.pause();
            }
        });

        binding.videoReplay.setOnClickListener(view -> videoView.resume());
    }
}