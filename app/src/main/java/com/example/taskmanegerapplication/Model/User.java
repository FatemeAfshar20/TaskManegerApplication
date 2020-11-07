package com.example.taskmanegerapplication.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID mId;
    private String mUsername;
    private String mPass;

    public User() {
        mId=UUID.randomUUID();
    }

    public User(UUID id) {
        mId = id;
    }

    public User(String username, String pass) {
        this();
        mUsername = username;
        mPass = pass;
    }

    public User(UUID id, String username, String pass) {
        this(id);
        mUsername = username;
        mPass = pass;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPass() {
        return mPass;
    }

    public void setPass(String pass) {
        mPass = pass;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(mId, user.mId) &&
                Objects.equals(mUsername, user.mUsername) &&
                Objects.equals(mPass, user.mPass);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(mId, mUsername, mPass);
    }
}
