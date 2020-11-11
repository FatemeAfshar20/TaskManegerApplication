package com.example.taskmanegerapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.taskmanegerapplication.DataBase.TaskManagerSchema.Task.TaskColumns;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
@Entity
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long mId;
    @ColumnInfo(name = TaskColumns.UUID)
    private UUID mUUID;
    @ColumnInfo(name = TaskColumns.TITLE)
    private String mTitle;
    @ColumnInfo(name = TaskColumns.CONTENT)
    private String mContent;
    @ColumnInfo(name = TaskColumns.DATE)
    private Date mDate;
    @ColumnInfo(name = TaskColumns.TIME)
    private Date mTime;
    @ColumnInfo(name = TaskColumns.STATE)
    private TaskState mState;
    @ColumnInfo(name = TaskColumns.USERID)
    private UUID mUserId;

    public Task() {
        this(UUID.randomUUID());
    }

    public Task(UUID UUID) {
        mUUID = UUID;
        mDate = new Date();
        mTime = new Date();
    }

    public Task(UUID UUID, String taskTitle,
                String taskContent, TaskState taskState,
                Date taskDate, Date taskTime, UUID userId) {
        this(UUID);
        mTitle = taskTitle;
        mContent = taskContent;
        mState = taskState;
        mDate = taskDate;
        mTime = taskTime;
        mUserId = userId;
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public TaskState getState() {
        return mState;
    }

    public void setState(TaskState state) {
        mState = state;
    }

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID userId) {
        mUserId = userId;
    }
}
