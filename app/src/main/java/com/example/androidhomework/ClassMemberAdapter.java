package com.example.androidhomework;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhomework.databinding.ClassMemberItemBinding;

import java.util.ArrayList;

public class ClassMemberAdapter extends RecyclerView.Adapter<ClassMemberAdapter.ViewHolder> {

    private final ArrayList<ClassMember> classMemberList;
    private ClassMemberItemLongClickListener longClickListener;

    // point3: 解除Adapter和Activity的耦合关系
    public ClassMemberAdapter(ArrayList<ClassMember> classMemberList, ClassMemberItemLongClickListener longClickListener) {
        this.classMemberList = classMemberList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClassMemberItemBinding binding = ClassMemberItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        // 长按监听：删除item
        viewHolder.itemView.setOnLongClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            // mark：用longClickListener替换直接调用ClassMemberActivity的方法
            // 将长按item对应的学生姓名发送至ClassMemberActivity
//            ClassMemberActivity.mClassMemberActivityTodo(ClassMemberActivity.LONG_CLICK, classMemberList.get(position).getNameText());
            this.longClickListener.onItemLongClick("long_click", classMemberList.get(position).getNameText());
            // 在ArrayList中移除此段
            classMemberList.remove(position);
            // 通知移除该item
            notifyItemRemoved(position);
            // 通知调制ArrayList顺序
            notifyItemRangeChanged(position, classMemberList.size());
            return false;
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassMember member = classMemberList.get(position);
        holder.nameText.setText(member.getNameText());
        holder.classText.setText(member.getClassText());
    }

    @Override
    public int getItemCount() {
        return classMemberList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameText, classText;

        public ViewHolder(@NonNull ClassMemberItemBinding binding) {
            super(binding.getRoot());

            nameText = binding.nameText;
            classText = binding.classText;
        }
    }

    public static interface ClassMemberItemLongClickListener {
        public void onItemLongClick(String event, String itemName);
    }
}
