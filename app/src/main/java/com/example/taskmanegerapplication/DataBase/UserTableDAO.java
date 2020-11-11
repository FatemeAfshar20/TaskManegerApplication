package com.example.taskmanegerapplication.DataBase;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanegerapplication.Model.User;

import java.util.List;
import java.util.UUID;

public interface UserTableDAO {
    @Query(value = "SELECT * FROM USER")
    List<User> getList();
    @Query(value = "SELECT * FROM User WHERE userId=:userId")
    User get(UUID userId);
    @Delete
    void delete(User element);
    @Insert
    void insert(User element);
    @Update
    void update(User element);
}
