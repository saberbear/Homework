package com.example.homework;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.homework.databinding.ActivityMainBinding;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private Uri imageUri;

    ActivityResultLauncher<Uri> takePhoto = registerForActivityResult(
            new ActivityResultContracts.TakePicture(), result -> {
                if (result) {
                    if (imageUri != null) {
                        imageView.setImageURI(imageUri);
                    }
                }
            });

    ActivityResultLauncher<String> selectPicture = registerForActivityResult(
            new ActivityResultContracts.GetContent(), uri -> imageView.setImageURI(uri));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageView = binding.imageView;

        binding.takePhotoButton.setOnClickListener(view -> {
            File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(MainActivity.this,
                        "com.example.homework.provider",
                        outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }

            takePhoto.launch(imageUri);
        });

        binding.fromAlbumButton.setOnClickListener(view -> selectPicture.launch("image/*"));
    }
}