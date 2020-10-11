package com.example.simplenotes;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;


    @NonNull

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDesc;

    @ColumnInfo(name = "DateNote")
    private long mDate;

    @ColumnInfo(name = "deadline")
    private long mDeadline;

    @ColumnInfo(name = "checkDeadline")
    private boolean mCheckDead;


    public Note(@NonNull String mTitle, String mDesc, long mDeadline, boolean mCheckDead) {
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mDeadline = mDeadline;
        this.mCheckDead = mCheckDead;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long mDate) {
        this.mDate = mDate;
    }

    public long getDeadline() {
        return mDeadline;
    }

    public boolean isCheckDead() {
        return mCheckDead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDesc() {
        return mDesc;
    }






}
