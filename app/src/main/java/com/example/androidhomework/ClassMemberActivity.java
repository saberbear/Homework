package com.example.androidhomework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidhomework.databinding.ActivityClassMemberBinding;

import java.util.ArrayList;

public class ClassMemberActivity extends BaseActivity {

    public static final String LONG_CLICK = "long_click";

    private static boolean onLongClickFlag = false;

    private static String removeNameText = "";

    private ArrayList<ClassMember> classMemberList;

    ClassDatabaseCollector db = new ClassDatabaseCollector(this, ClassDatabaseHelper.DATABASE_NAME, ClassDatabaseHelper.DATABASE_VERSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityClassMemberBinding binding = ActivityClassMemberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 如果数据库不存在：创建&添加初始数据
        if (db.queryData(ClassDatabaseHelper.TABLE_NAME).size() == 0) {
            db.create();
            initClassMember();
            for (int i = 0; i < classMemberList.size(); i++) {
                db.addData(classMemberList.get(i));
            }
        }
        // 重新填充classMemberList数据，避免从其他activity回来之后数据有改变
        classMemberList.clear();
        classMemberList = db.queryData(ClassDatabaseHelper.TABLE_NAME);
        // 班级人员信息RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        ClassMemberAdapter adapter = new ClassMemberAdapter(classMemberList);
        binding.recyclerView.setAdapter(adapter);

        if (onLongClickFlag) {
            if (!removeNameText.isEmpty()) {
                // 删除数据库中的对应数据
                db.deleteData(removeNameText);
            }
            onLongClickFlag = false;
            removeNameText = "";
        }

        binding.forceQuitButton.setOnClickListener(view -> {
            Intent intent = new Intent(FORCE_QUIT_BROADCAST_INTENT);
            sendBroadcast(intent);
        });

        binding.addClassMemberButton.setOnClickListener(view -> {
            EditText inputName = new EditText(ClassMemberActivity.this);
            EditText inputClass = new EditText(ClassMemberActivity.this);
            inputName.setHint("同学名");
            inputClass.setHint("班级");

            AlertDialog.Builder builder = new AlertDialog.Builder(ClassMemberActivity.this);
            builder.setTitle("添加新同学");
            builder.setView(viewGroup(inputName, inputClass));
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                String nameText = inputName.getText().toString();
                String classText = inputClass.getText().toString();
                if (nameText.isEmpty() || classText.isEmpty())
                    Toast.makeText(this, "输入为空", Toast.LENGTH_SHORT).show();
                else {
                    classMemberList.add(new ClassMember(nameText, classText));
                    db.addData(classMemberList.get(classMemberList.size() - 1));
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });
    }
    // 双EditText视图（姓名、班级）
    public ViewGroup viewGroup(View view1, View view2) {
        LinearLayout mLinearLayout = new LinearLayout(this);
        // 设置布局宽和高
        mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 设置布局方向
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        // 建立子布局样式
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 父布局中添加子布局和子布局样式
        mLinearLayout.addView(view1, mLayoutParams);
        mLinearLayout.addView(view2, mLayoutParams);

        return mLinearLayout;
    }
    // 初始化classMemberList
    private void initClassMember() {
        classMemberList.add(new ClassMember("张三", "三班"));
        classMemberList.add(new ClassMember("李四", "四班"));
        classMemberList.add(new ClassMember("王五", "五班"));
    }
    // 供外部类调用的static方法（长按删除通知）
    public static void mClassMemberActivityTodo(String event, String nameText) {
        switch (event) {
            case LONG_CLICK: {
                onLongClickFlag = true;
                removeNameText = nameText;
            }
        }
    }

}