package com.example.androidhomework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClassDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ClassMember.db";

    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "class_info";

    public static final String TABLE_CREATE_SQL = "create table " + TABLE_NAME + "(" +
            "id integer primary key autoincrement," +
            "name text," +
            "class text)";

    public ClassDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_SQL);
        db.execSQL("insert into " + TABLE_NAME + "(name, class) values ('张三', '三班')");
        db.execSQL("insert into " + TABLE_NAME + "(name, class) values ('李四', '四班')");
        db.execSQL("insert into " + TABLE_NAME + "(name, class) values ('王五', '五班')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
