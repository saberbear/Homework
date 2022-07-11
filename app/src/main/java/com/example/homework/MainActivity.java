package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addData.setOnClickListener(view -> {
            Uri uri = Uri.parse("content://com.example.homework.provider/book");
            ContentValues values = new ContentValues();
            values.put("name", "三体：地球往事");
            values.put("author", "刘慈欣");
            values.put("pages", 302);
            values.put("price", 23.23);
            Uri newUri = getContentResolver().insert(uri, values);
            newId = newUri.getPathSegments().get(1);
        });

        binding.queryData.setOnClickListener(view -> {
            Uri uri = Uri.parse("content://com.example.homework.provider/book");
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("author"));
                    @SuppressLint("Range") int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                    @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));
                    Log.d("MainActivity", "book name is " + name);
                    Log.d("MainActivity", "book author is " + author);
                    Log.d("MainActivity", "book pages is " + pages);
                    Log.d("MainActivity", "book price is " + price);
                }
                cursor.close();
            }
        });

        binding.updateData.setOnClickListener(view -> {
            Uri uri = Uri.parse("content://com.example.homework.provider/book/" + newId);
            ContentValues values = new ContentValues();
            values.put("name", "三体2：黑暗森林");
            values.put("author", "刘慈欣");
            values.put("pages", 470);
            values.put("price", 32.32);
            getContentResolver().update(uri, values, null, null);
        });

        binding.deleteData.setOnClickListener(view -> {
            Uri uri = Uri.parse("content://com.example.homework.provider/book/" + newId);
            getContentResolver().delete(uri, null, null);
        });
    }
}