package com.example.taskmanegerapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanegerapplication.Model.Task;
import com.example.taskmanegerapplication.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.Holder> {
    private List<Task> mTasks;
    private Context mContext;

    public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

    public StateAdapter(List<Task> tasks, Context context) {
        mTasks = tasks;
        mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.item_view,
                parent,
                false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.bind(mTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private MaterialTextView mTaskTitle,mTaskDate,
                mTaskTime,mTaskImg;
        private AppCompatImageButton mBtnEdit,mBtnDelete,mBtnShow;

        public Holder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(@NonNull View itemView) {
            mTaskTitle=itemView.findViewById(R.id.task_title);
            mTaskDate=itemView.findViewById(R.id.task_date);
            mTaskTime=itemView.findViewById(R.id.task_time);
            mTaskImg=itemView.findViewById(R.id.task_img);

            mBtnEdit=itemView.findViewById(R.id.btn_edit);
            mBtnDelete=itemView.findViewById(R.id.btn_delete);
            mBtnShow=itemView.findViewById(R.id.btn_show);
        }

        public void bind(Task task){
            mTaskTitle.setText(task.getTitle());
            mTaskDate.setText(task.getDate().toString());
            mTaskTitle.setText(task.getTime().toString());
            mTaskImg.setText(task.getTitle().substring(0,1));
        }
    }
}
