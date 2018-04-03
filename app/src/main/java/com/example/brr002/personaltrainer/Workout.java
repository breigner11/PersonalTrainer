package com.example.brr002.personaltrainer;

import java.util.Date;
import java.util.UUID;

public class Workout {

    private UUID mId;
    private String mFocus;
    private Date mDate;
    private boolean mCompleted;

    public Workout() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getFocus() {
        return mFocus;
    }

    public void setFocus(String focus) {
        mFocus = focus;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
