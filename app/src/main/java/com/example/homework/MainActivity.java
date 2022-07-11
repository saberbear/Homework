package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        binding.createDatabase.setOnClickListener(view -> dbHelper.getWritableDatabase());

        binding.addData.setOnClickListener(view -> {
            db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
                    new String[] {"The Da Vinci Code", "Dan Brown", "454", "16.96"});
            db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
                    new String[] {"The Lost Symbol", "Dan Brown", "510", "19.95"});
            Log.i("add_data", "Add succeeded");
        });

        binding.updateData.setOnClickListener(view -> {
            db.execSQL("update Book set price = ? where name = ?",
                    new String[] {"10.99", "The Da Vinci Code"});
            Log.i("update_data", "Update succeeded");
        });

        binding.deleteData.setOnClickListener(view -> {
            db.execSQL("delete from Book where pages > ?",
                    new String[] {"500"});
            Log.i("delete_data", "Delete succeeded");
        });

        binding.queryData.setOnClickListener(view -> {
            Cursor cursor = db.rawQuery("select * from Book", null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("author"));
                    @SuppressLint("Range") String pages = cursor.getString(cursor.getColumnIndex("pages"));
                    @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
                    Log.d("MainActivity", "book name is " + name);
                    Log.d("MainActivity", "book author is " + author);
                    Log.d("MainActivity", "book pages is " + pages);
                    Log.d("MainActivity", "book price is " + price);
                } while (cursor.moveToNext());
            }
            cursor.close();
        });

        binding.replaceData.setOnClickListener(view -> {
            db.beginTransaction();
            try {
                db.execSQL("delete from Book");
                db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
                        new String[] {"Game of Thrones", "George Martin", "720", "20.85"});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
        });
    }
}