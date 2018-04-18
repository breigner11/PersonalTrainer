package com.example.brr002.personaltrainer.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.brr002.personaltrainer.Workout;

import java.util.Date;
import java.util.UUID;

public class WorkoutCursorWrapper extends CursorWrapper {
    public WorkoutCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Workout getWorkout(){
        String uuidString = getString(getColumnIndex(WorkoutDB.WorkoutTable.Cols.UUID));
        String focus = getString(getColumnIndex(WorkoutDB.WorkoutTable.Cols.TITLE));
        long date = getLong(getColumnIndex(WorkoutDB.WorkoutTable.Cols.DATE));
        int isCompleted = getInt(getColumnIndex(WorkoutDB.WorkoutTable.Cols.COMPLETED));

        Workout workout = new Workout(UUID.fromString(uuidString));
        workout.setFocus(focus);
        workout.setDate(new Date(date));
        workout.setCompleted(isCompleted != 0);

        return workout;
    }
}
