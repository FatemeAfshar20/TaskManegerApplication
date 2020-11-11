package com.example.taskmanegerapplication.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskmanegerapplication.DataBase.TaskManagerSchema.User.UserColumns;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private long mId;
    @ColumnInfo(name = UserColumns.UUID)
    private UUID mUUID;
    @ColumnInfo(name = UserColumns.USERNAME)
    private String mUsername;
    @ColumnInfo(name = UserColumns.PASSWORD)
    private String mPass;
    @ColumnInfo(name = UserColumns.MEMBERSHIP)
    private Date mMemberSheep;
    @ColumnInfo(name = UserColumns.ISADMIN)
    private boolean mIsAdmin=false;

    public User() {
        mUUID =UUID.randomUUID();
        mMemberSheep=new Date();
    }

    public User(UUID id) {
        mUUID = id;
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

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
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

    public Date getMemberSheep() {
        return mMemberSheep;
    }

    public void setMemberSheep(Date memberSheep) {
        mMemberSheep = memberSheep;
    }

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public void setAdmin(boolean admin) {
        mIsAdmin = admin;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(mUUID, user.mUUID) &&
                Objects.equals(mUsername, user.mUsername) &&
                Objects.equals(mPass, user.mPass);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(mUUID, mUsername, mPass);
    }
}
