package com.example.taskmanegerapplication.Model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID mId;
    private String mTitle;
    private String mContent;
    private Date mDate;
    private Date mTime;
    private TaskState mState;
    private UUID mUserId;

    public Task() {
        this(UUID.randomUUID());
    }

    public Task(UUID UUID) {
        mId = UUID;
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

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
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
