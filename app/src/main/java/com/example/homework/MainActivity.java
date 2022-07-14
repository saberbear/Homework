package com.example.homework;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    ActivityResultLauncher<Void> takePhoto = registerForActivityResult(
            new ActivityResultContracts.TakePicturePreview(), bitmap -> binding.imageView.setImageBitmap(bitmap));

    ActivityResultLauncher<String> selectMultiplePicture = registerForActivityResult(
            new ActivityResultContracts.GetMultipleContents(), uriList -> {
                if (uriList.size() > 1) {
                    binding.imageView.setImageURI(uriList.get(0));
                    binding.imageView2.setImageURI(uriList.get(1));
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.takePhotoButton.setOnClickListener(view -> takePhoto.launch(null));

        binding.fromAlbumButton.setOnClickListener(view -> selectMultiplePicture.launch("image/*"));
    }
}