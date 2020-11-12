package com.example.taskmanegerapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanegerapplication.Model.Task;
import com.example.taskmanegerapplication.R;
import com.example.taskmanegerapplication.Utils.DateTimeFormat;
import com.example.taskmanegerapplication.Utils.PhotoUtils;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.Holder> implements Filterable {
    private List<Task> mTasks;

    private List<Task> mSearchResult;
    private Context mContext;
    private OnIconSelectListener mCallbacks;

    public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

    public StateAdapter(List<Task> tasks, Context context,OnIconSelectListener onIconSelectListener) {
        mTasks = tasks;
        mContext = context;
        mCallbacks=onIconSelectListener;
        mSearchResult=new ArrayList<>(tasks);
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


    private Filter mFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Task> resultList=new ArrayList<>();
            if (constraint==null || constraint.length()==0)
                resultList.addAll(mSearchResult);
            else {

                String filterPattern=constraint.toString().toLowerCase().trim();
                for (Task task : mSearchResult) {
                    if (task.getTitle().contains(filterPattern))
                        resultList.add(task);
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=resultList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                mTasks.clear();
                mTasks.addAll((List)results.values);
                notifyDataSetChanged();
        }
    };
    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private MaterialTextView mTaskTitle, mTaskDate,
                mTaskTime;
        private AppCompatImageButton mBtnShow, mCamera, mShare;
        private AppCompatImageView mTaskImg;
        private Task mTask;

        public Holder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
            setListener();
        }

        private void findViews(@NonNull View itemView) {
            mTaskTitle = itemView.findViewById(R.id.task_title);
            mTaskDate = itemView.findViewById(R.id.task_date);
            mTaskTime = itemView.findViewById(R.id.task_time);
            mTaskImg = itemView.findViewById(R.id.task_img);

            mBtnShow = itemView.findViewById(R.id.btn_show);
            mCamera = itemView.findViewById(R.id.camera);
            mShare = itemView.findViewById(R.id.share);
        }

        public void bind(Task task) {
            mTask = task;
            mTaskTitle.setText(task.getTitle());
            mTaskDate.setText(DateTimeFormat.getDateFormat(task.getDate()));
            mTaskTime.setText(DateTimeFormat.getTimeFormat(task.getDate()));

            Bitmap image = PhotoUtils.getScalePhoto(
                    mTask.getImgAddress(), (Activity) mContext);

            mTaskImg.setImageBitmap(image);
        }

        public void setListener() {
            mBtnShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallbacks.onSelectShowBtn(mTask.getUUID());
                }
            });

            mShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallbacks.onShareTaskInfo(
                            "Task Title: " + mTask.getTitle() +
                                    "Task Content: " + mTask.getContent() +
                                    "Task Time: " + mTask.getTime() +
                                    "Task Date: " + mTask.getDate() +
                                    "Task State: " + mTask.getState());
                }
            });

            mCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String path=mCallbacks.onTakePhoto(mTask);
                    mTask.setImgAddress(path);

                    mCallbacks.onSetImage(mTaskImg);
                }
            });
        }
    }

    public interface OnIconSelectListener {
        void onSelectShowBtn(UUID taskId);

        void onShareTaskInfo(String taskInfo);

        String onTakePhoto(Task task);

        void onSetImage(AppCompatImageView imageView);
    }
}
