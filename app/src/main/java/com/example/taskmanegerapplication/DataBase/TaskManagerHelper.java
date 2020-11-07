package com.example.taskmanegerapplication.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.taskmanegerapplication.DataBase.TaskManagerSchema.Task.TaskColumns;
import static com.example.taskmanegerapplication.DataBase.TaskManagerSchema.User.UserColumns;

public class TaskManagerHelper extends SQLiteOpenHelper {


    public TaskManagerHelper(@Nullable Context context) {
        super(context, TaskManagerSchema.NAME,
                null,
                TaskManagerSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder queryTask = new StringBuilder();
        queryTask.append("CREATE TABLE " + TaskManagerSchema.Task.NAME + " ( ");
        queryTask.append(TaskColumns.ID + " TEXT NOT NULL ,");
        queryTask.append(TaskColumns.TITLE + " TEXT, ");
        queryTask.append(TaskColumns.CONTENT + " TEXT, ");
        queryTask.append(TaskColumns.DATE + " TEXT, ");
        queryTask.append(TaskColumns.TIME + " TEXT, ");
        queryTask.append(TaskColumns.STATE + " TEXT, ");
        queryTask.append(TaskColumns.USERID + " TEXT NOT NULL");
        queryTask.append(" ); ");
        db.execSQL(queryTask.toString());

        StringBuilder queryUser=new StringBuilder();
        queryUser.append("CREATE TABLE "+TaskManagerSchema.User.NAME+" ( ");
        queryUser.append(UserColumns.ID+" INTEGER PRIMARY KEY NOT NULL , ");
        queryUser.append(UserColumns.UUID+" TEXT NOT NULL , ");
        queryUser.append(UserColumns.USERNAME+" TEXT NOT NULL, ");
        queryUser.append(UserColumns.PASSWORD+" TEXT NOT NULL, ");
        queryUser.append(UserColumns.MEMBERSHIP+" TEXT, ");
        queryUser.append(UserColumns.ISADMIN+" INTEGER ");
        queryUser.append(" ); ");

        db.execSQL(queryUser.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
