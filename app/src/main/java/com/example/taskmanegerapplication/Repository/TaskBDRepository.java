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

    public List<Task> getTodoList(){
        List<Task> tasks=new ArrayList<>();
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getState().toString().equals("TODO"))
                tasks.add(getList().get(i));
        }
        return tasks;
    }

    public List<Task> getDoingList(){
        List<Task> tasks=new ArrayList<>();
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getState().toString().equals("DOING"))
                tasks.add(getList().get(i));
        }
        return tasks;
    }

    public List<Task> getDoneList(){
        List<Task> tasks=new ArrayList<>();
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getState().toString().equals("DONE"))
                tasks.add(getList().get(i));
        }
        return tasks;
    }
}
