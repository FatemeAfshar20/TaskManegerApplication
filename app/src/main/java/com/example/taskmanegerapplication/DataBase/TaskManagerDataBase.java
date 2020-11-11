package com.example.taskmanegerapplication.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmanegerapplication.Model.Task;
import com.example.taskmanegerapplication.Model.User;

@Database(entities = {Task.class, User.class},version = TaskManagerSchema.VERSION)
@TypeConverters({Converter.class})
public abstract class TaskManagerDataBase extends RoomDatabase {

    public abstract UserTableDAO getUserDAO();
    public abstract TaskTableDAO getTableDAO();

}
