package com.example.taskmanegerapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.taskmanegerapplication.DataBase.TaskManagerSchema.Task.TaskColumns;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
@Entity
public class Task  implements Serializable {
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
    @ColumnInfo(name = TaskColumns.IMG_ADDRESS)
    private String mImgAddress;

    public Task() {
        this(UUID.randomUUID());
        mImgAddress= "";
    }

    public Task(UUID UUID) {
        mUUID = UUID;
        mDate = new Date();
        mTime = new Date();
    }

    public Task(
                UUID UUID,
                String title,
                String content,
                Date date,
                Date time,
                TaskState state,
                UUID userId,String imageAddress) {

        mUUID = UUID;
        mTitle = title;
        mContent = content;
        mDate = date;
        mTime = time;
        mState = state;
        mUserId = userId;
        mImgAddress=imageAddress;
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

    public String getImgAddress() {
        return mImgAddress;
    }

    public void setImgAddress(String imgAddress) {
        mImgAddress = imgAddress;
    }
}
