package com.example.androidhomework;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhomework.databinding.ClassMemberItemBinding;

import java.util.ArrayList;

public class ClassMemberAdapter extends RecyclerView.Adapter<ClassMemberAdapter.ViewHolder> {

    private ArrayList<ClassMember> classMemberList;

    private ClassDatabaseCollector dbCollector;

    public ClassMemberAdapter(ArrayList<ClassMember> classMemberList, ClassDatabaseCollector dbCollector) {
        this.classMemberList = classMemberList;
        this.dbCollector = dbCollector;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClassMemberItemBinding binding = ClassMemberItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        // 长按监听：删除item
        viewHolder.itemView.setOnLongClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            // 在数据库中移除此段
            dbCollector.deleteData(classMemberList.get(position).getNameText());
            // 在ArrayList中移除此段
            classMemberList.remove(position);
            // 通知移除该item
            notifyItemRemoved(position);
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

}
