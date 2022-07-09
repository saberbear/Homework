package com.example.androidhomework;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhomework.databinding.ActivityClassMemberBinding;

import java.util.ArrayList;

public class ClassMemberActivity extends BaseActivity {

    private final String DEFAULT_COLOR = "#bdbdbd";

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        ClassMemberAdapter adapter = new ClassMemberAdapter(classMemberList, dbCollector);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new ClassMemberItemDecoration());    // 设置RecyclerView的边框线

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
                    ClassMember newMember = new ClassMember(nameText, classText);
                    dbCollector.addData(new ClassMember(nameText, classText));
                    classMemberList.add(newMember);
                    binding.recyclerView.getAdapter().notifyItemInserted(classMemberList.size() - 1);
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

    // 绘制RecyclerView的边框线
    public class ClassMemberItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDraw(c, parent, state);

            Paint paint = new Paint();
            paint.setStrokeWidth(5);
            paint.setColor(Color.parseColor(DEFAULT_COLOR));

            int childCount = parent.getChildCount();    // 获得RecyclerView中总条目数量
            for (int i = 0; i < childCount; i++) {
                View childView = parent.getChildAt(i);  // 获得子View，也就是一个条目的View，准备给它画上边框
                // 获得子View在屏幕上的位置，以及长和宽，方便我们得到边框的具体坐标
                float x = childView.getX();
                float y = childView.getY();
                int width = childView.getWidth();
                int height = childView.getHeight();
                // 根据这些点画条目四周的线
                c.drawLine(x, y, x + width, y, paint);
                c.drawLine(x, y, x, y + height, paint);
                c.drawLine(x + width, y, x + width, y + height, paint);
                c.drawLine(x, y + height, x + width, y + height, paint);
            }
        }
    }

}