package com.example.androidhomework;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ClassDatabaseCollector {

    private Context context;

    private String tableName;

    private int version;

    private ClassDatabaseHelper dbHelper = new ClassDatabaseHelper(context, tableName, null, version);

    public ClassDatabaseCollector(Context context, String tableName, int version) {
        this.context = context;
        this.tableName = tableName;
        this.version = version;
    }
    // 创建数据库
    public void create() {
        dbHelper.getWritableDatabase();
    }
    // 添加数据
    public void addData(ClassMember member) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", member.getNameText());
        values.put("class", member.getClassText());
        db.insert(tableName, null, values);
    }
    // 遍历数据库
    public ArrayList<ClassMember> queryData(String tableName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<ClassMember> classMemberList = new ArrayList<>();

        Cursor cursor = db.query(tableName, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nameText = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String classText = cursor.getString(cursor.getColumnIndex("class"));
                classMemberList.add(new ClassMember(nameText, classText));
            } while (cursor.moveToNext());
            cursor.close();
            return classMemberList;
        }

        cursor.close();
        return classMemberList;
    }
    // 删除数据（根据学生姓名）
    public void deleteData(String nameText) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(tableName, "name = ?", new String[]{nameText});
    }
}
