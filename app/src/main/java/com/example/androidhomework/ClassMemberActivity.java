package com.example.androidhomework;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    // point2: 使用属性要考虑属性是否已经被初始化
    // private ArrayList<ClassMember> classMemberList = new ArrayList<>();
    private ClassDatabaseCollector dbCollector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityClassMemberBinding binding = ActivityClassMemberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 数据库初始化
        dbCollector = new ClassDatabaseCollector(this, ClassDatabaseHelper.TABLE_NAME, ClassDatabaseHelper.DATABASE_VERSION);

        // 列表视图初始化
        ArrayList<ClassMember> classMemberList = dbCollector.queryData();
        Log.i("dbQueryResult", "数据库表查询结果：" + classMemberList.toString());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        ClassMemberAdapter adapter = new ClassMemberAdapter(classMemberList);
        binding.recyclerView.setAdapter(adapter);

        if (onLongClickFlag) {
            if (!removeNameText.isEmpty()) {
                // 删除数据库中的对应数据
                dbCollector.deleteData(removeNameText);
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
            builder.setView(this.addClassMemberAlertView(inputName, inputClass));
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                String nameText = inputName.getText().toString();
                String classText = inputClass.getText().toString();
                if (nameText.isEmpty() || classText.isEmpty())
                    Toast.makeText(this, "输入为空", Toast.LENGTH_SHORT).show();
                else {
                    ClassMember newMemeber = new ClassMember(nameText, classText);
                    dbCollector.addData(new ClassMember(nameText, classText));
                    classMemberList.add(newMemeber);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });
    }
    // 双EditText视图（姓名、班级）
    public ViewGroup addClassMemberAlertView(View view1, View view2) {
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