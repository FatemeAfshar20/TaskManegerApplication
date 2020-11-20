package com.example.taskmanegerapplication.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Room;

import com.example.taskmanegerapplication.DataBase.TaskManagerDataBase;
import com.example.taskmanegerapplication.DataBase.TaskManagerHelper;
import com.example.taskmanegerapplication.DataBase.TaskManagerSchema;
import com.example.taskmanegerapplication.DataBase.UserTableDAO;
import com.example.taskmanegerapplication.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements IRepository<User>{
    private static UserDBRepository sInstance;
    private UserTableDAO mDAO;
    private Context mContext;
    private List<User> mUserList=new ArrayList<>();

    private UserDBRepository(Context context) {
        mContext=context.getApplicationContext();
/*        TaskManagerHelper taskManagerHelper=
                new TaskManagerHelper(context);
        mDatabase=taskManagerHelper.getWritableDatabase();*/

        TaskManagerDataBase dataBase=
                Room.databaseBuilder
                        (mContext,TaskManagerDataBase.class,
                                TaskManagerSchema.NAME).
                        allowMainThreadQueries().build();

        mDAO=dataBase.getUserDAO();
    }

    public static UserDBRepository getInstance(Context context) {
        if (sInstance==null)
            sInstance=new UserDBRepository(context);
        return sInstance;
    }

    @Override
    public List<User> getList() {
        return mDAO.getList();
    }

    @Override
    public User get(UUID userId) {
/*        for (int i = 0; i < mUserList.size(); i++) {
            if(mUserList.get(i).getUsername().equals(userId.toString()))
                return mUserList.get(i);
        }
        return null;*/
        return mDAO.get(userId);
    }

    public User get(String username) {
/*        for (int i = 0; i < mUserList.size(); i++) {
            if(mUserList.get(i).getUsername().equals(username))
                return mUserList.get(i);
        }
        return null;*/
        return mDAO.get(username);
    }

    @Override
    public void delete(User user) {
            mDAO.delete(user);
    }

    @Override
    public void insert(User user) {
            mDAO.insert(user);
    }

    @Override
    public void update(User user) {
            mDAO.update(user);
    }

    public boolean userExist(String username){
        if (get(username) != null)
            return true;
        return false;
    }

    public void deleteAll() {
        for (int i = 0; i < getList().size(); i++) {
            if (!getList().get(i).isAdmin()) {
                delete(getList().get(i));
                i--;
            }
        }
    }
}
