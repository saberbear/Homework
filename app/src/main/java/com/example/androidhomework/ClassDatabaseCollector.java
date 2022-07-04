package com.example.androidhomework;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ClassDatabaseCollector {

    private final Context context;

    private final String tableName;

    private final int version;

    private final SQLiteDatabase db;
    // point1:
    // 代码规范：类属性应该在方法里初始化，不要直接初始化。
    // 代码原理：类属性初始化的时机先于构造器执行
    public ClassDatabaseCollector(Context context, String tableName, int version) {
        this.context = context;
        this.tableName = tableName;
        this.version = version;
        ClassDatabaseHelper dbHelper = new ClassDatabaseHelper(context, ClassDatabaseHelper.DATABASE_NAME, null, version);
        db = dbHelper.getWritableDatabase();
    }

    // 添加数据
    public void addData(ClassMember member) {
        ContentValues values = new ContentValues();
        values.put("name", member.getNameText());
        values.put("class", member.getClassText());
        db.insert(tableName, null, values);
    }
    // 遍历数据库
    public ArrayList<ClassMember> queryData() {
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
        db.delete(tableName, "name = ?", new String[]{nameText});
    }

}
