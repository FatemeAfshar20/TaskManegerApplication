package com.example.taskmanegerapplication.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Room;

import com.example.taskmanegerapplication.DataBase.TaskManagerDataBase;
import com.example.taskmanegerapplication.DataBase.TaskManagerHelper;
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
       /* TaskManagerHelper taskManagerHelper=
                new TaskManagerHelper(context);
        mDatabase=taskManagerHelper.getWritableDatabase();*/
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
/*        List<Task> tasks=new ArrayList<>();
       for (Task task:getList()){
           if (task.getUserId().equals(userId))
               tasks.add(task);
       }
       return tasks;*/

        return mDAO.getListWithUserId(userId);
    }

    @Override
    public Task get(UUID taskId) {
        return mDAO.get(taskId);
/*        for (Task task : getList()) {
            if (task.getUUID().equals(taskId))
                return task;
        }
        return null;*/
    }

    @Override
    public void delete(Task element) {
        mDAO.delete(element);
        /*for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).equals(element))
                getList().remove(element);
        }*/
    }

    @Override
    public void insert(Task element) {
        mDAO.insert(element);
           /* mTaskList.add(element);*/
    }

    @Override
    public void update(Task element) {
        mDAO.update(element);
        /*for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getUUID().equals(element.getUUID())){
                getList().remove(i);
                getList().add(i,element);
            }
        }*/
    }

    public List<Task> getListWithState(String state,UUID userId){
        return mDAO.getListWithState(state,userId);
    }

   /* public List<Task> getTodoList(UUID userId){
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
    }*/
}
