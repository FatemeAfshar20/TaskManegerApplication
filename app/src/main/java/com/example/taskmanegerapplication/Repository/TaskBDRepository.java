package com.example.taskmanegerapplication.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanegerapplication.DataBase.TaskManagerHelper;
import com.example.taskmanegerapplication.Model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskBDRepository implements IRepository<Task>{
    private static TaskBDRepository sInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private List<Task> mTaskList=new ArrayList<>();

    private TaskBDRepository(Context context) {
        mContext=context.getApplicationContext();
        TaskManagerHelper taskManagerHelper=
                new TaskManagerHelper(context);
        mDatabase=taskManagerHelper.getWritableDatabase();
    }

    public static TaskBDRepository getInstance(Context context) {
        if (sInstance==null)
            sInstance=new TaskBDRepository(context);
        return sInstance;
    }

    @Override
    public List<Task> getList() {
        return mTaskList;
    }


    public List<Task> getListWithUserId(UUID userId) {
        List<Task> tasks=new ArrayList<>();
       for (Task task:getList()){
           if (task.getUserId().equals(userId))
               tasks.add(task);
       }
       return tasks;
    }

    @Override
    public Task get(UUID uuid) {
        return null;
    }

    @Override
    public void delete(Task element) {

    }

    @Override
    public void insert(Task element) {
            mTaskList.add(element);
    }

    @Override
    public void update(Task element) {

    }

    public List<Task> getTodoList(UUID userId){
        List<Task> tasks=new ArrayList<>();
        for (int i = 0; i < getListWithUserId(userId).size(); i++) {
            if (getListWithUserId(userId).get(i).getState().toString().equals("TODO"))
                tasks.add(getListWithUserId(userId).get(i));
        }
        return tasks;
    }

    public List<Task> getDoingList(UUID userId){
        List<Task> tasks=new ArrayList<>();
        for (int i = 0; i < getListWithUserId(userId).size(); i++) {
            if (getListWithUserId(userId).get(i).getState().toString().equals("DOING"))
                tasks.add(getListWithUserId(userId).get(i));
        }
        return tasks;
    }

    public List<Task> getDoneList(UUID userId){
        List<Task> tasks=new ArrayList<>();
        for (int i = 0; i < getListWithUserId(userId).size(); i++) {
            if (getListWithUserId(userId).get(i).getState().toString().equals("DONE"))
                tasks.add(getListWithUserId(userId).get(i));
        }
        return tasks;
    }
}
