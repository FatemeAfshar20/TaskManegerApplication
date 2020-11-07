package com.example.taskmanegerapplication.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanegerapplication.DataBase.TaskManagerHelper;
import com.example.taskmanegerapplication.Model.Task;

import java.util.List;
import java.util.UUID;

public class TaskBDRepository implements IRepository<Task>{
    private static TaskBDRepository sInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;

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
        return null;
    }

    @Override
    public Task get(String uuid) {
        return null;
    }

    @Override
    public void delete(Task element) {

    }

    @Override
    public void insert(Task element) {

    }

    @Override
    public void update(Task element) {

    }
}
