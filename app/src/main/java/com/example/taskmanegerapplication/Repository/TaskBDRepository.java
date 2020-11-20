package com.example.taskmanegerapplication.Repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanegerapplication.DataBase.TaskManagerDataBase;
import com.example.taskmanegerapplication.DataBase.TaskManagerSchema;
import com.example.taskmanegerapplication.DataBase.TaskTableDAO;
import com.example.taskmanegerapplication.Model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskBDRepository implements IRepository<Task>{
    private static TaskBDRepository sInstance;
    private Context mContext;
    private TaskTableDAO mDAO;

    private List<Task> mTaskList=new ArrayList<>();

    private TaskBDRepository(Context context) {
        mContext=context.getApplicationContext();
        TaskManagerDataBase dataBase= Room.
                databaseBuilder(mContext,TaskManagerDataBase.class,
                        TaskManagerSchema.NAME).
                allowMainThreadQueries().build();

        mDAO=dataBase.getTableDAO();
    }

    public static TaskBDRepository getInstance(Context context) {
        if (sInstance==null)
            sInstance=new TaskBDRepository(context);
        return sInstance;
    }

    @Override
    public List<Task> getList() {
        return mDAO.getList();
    }


    public List<Task> getListWithUserId(UUID userId) {
        return mDAO.getListWithUserId(userId);
    }

    @Override
    public Task get(UUID taskId) {
        return mDAO.get(taskId);
    }

    @Override
    public void delete(Task element) {
        mDAO.delete(element);
    }

    @Override
    public void insert(Task element) {
        mDAO.insert(element);
    }

    @Override
    public void update(Task element) {
        mDAO.update(element);
    }

    public List<Task> getListWithState(String state,UUID userId){
        return mDAO.getListWithState(state,userId);
    }
}
