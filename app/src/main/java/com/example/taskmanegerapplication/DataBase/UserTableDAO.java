package com.example.taskmanegerapplication.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanegerapplication.Model.User;

import java.util.List;
import java.util.UUID;
@Dao
public interface UserTableDAO {
    @Query(value = "SELECT * FROM USER")
    List<User> getList();
    @Query(value = "SELECT * FROM User WHERE userId=:userId")
    User get(UUID userId);
    @Query(value = "SELECT * FROM User WHERE username=:username")
    User get(String username);
    @Delete
    void delete(User element);
    @Insert
    void insert(User element);
    @Update
    void update(User element);
}
