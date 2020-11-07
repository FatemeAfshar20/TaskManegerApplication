package com.example.taskmanegerapplication.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanegerapplication.DataBase.TaskManagerHelper;
import com.example.taskmanegerapplication.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements IRepository<User>{
    private static UserDBRepository sInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private List<User> mUserList=new ArrayList<>();

    private UserDBRepository(Context context) {
        mContext=context.getApplicationContext();
        TaskManagerHelper taskManagerHelper=
                new TaskManagerHelper(context);
        mDatabase=taskManagerHelper.getWritableDatabase();
    }

    public static UserDBRepository getInstance(Context context) {
        if (sInstance==null)
            sInstance=new UserDBRepository(context);
        return sInstance;
    }

    @Override
    public List<User> getList() {
        return null;
    }

    @Override
    public User get(String username) {
        for (int i = 0; i < mUserList.size(); i++) {
            if(mUserList.get(i).getUsername().equals(username))
                return mUserList.get(i);
        }
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void insert(User user) {
            mUserList.add(user);
    }

    @Override
    public void update(User user) {

    }

    public void getBeautyUser(){

    }

    public boolean userExist(String username){
        for (int i = 0; i < mUserList.size(); i++) {
            if(mUserList.get(i).getUsername().equals(username))
                return true;
        }
        return false;
    }
}
