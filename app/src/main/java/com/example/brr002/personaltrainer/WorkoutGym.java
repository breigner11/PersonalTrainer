package com.example.brr002.personaltrainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.brr002.personaltrainer.database.WorkoutBaseHelper;
import com.example.brr002.personaltrainer.database.WorkoutCursorWrapper;
import com.example.brr002.personaltrainer.database.WorkoutDB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkoutGym {
    private static WorkoutGym sWorkoutGym;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static WorkoutGym get(Context context){
        if (sWorkoutGym == null){
            sWorkoutGym = new WorkoutGym(context);
        }
        return sWorkoutGym;
    }

    private WorkoutGym(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new WorkoutBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addWorkout(Workout c){
        ContentValues values = getContentValues(c);

        mDatabase.insert(WorkoutDB.WorkoutTable.NAME, null, values);
    }

    public List<Workout> getWorkouts(){
        List<Workout> workouts = new ArrayList<>();

        WorkoutCursorWrapper cursor = queryCrimes(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                workouts.add(cursor.getWorkout());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return workouts;
    }

    public Workout getWorkout(UUID id){
        WorkoutCursorWrapper cursor = queryCrimes(
                WorkoutDB.WorkoutTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try{
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getWorkout();
        }finally {
            cursor.close();
        }
    }

    public void updateWorkout(Workout workout){
        String uuidString = workout.getId().toString();
        ContentValues values = getContentValues(workout);

        mDatabase.update(WorkoutDB.WorkoutTable.NAME, values,
                WorkoutDB.WorkoutTable.Cols.UUID + " = ?",
                new String[]{ uuidString});
    }

    private WorkoutCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                WorkoutDB.WorkoutTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new WorkoutCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Workout workout){
        ContentValues values = new ContentValues();
        values.put(WorkoutDB.WorkoutTable.Cols.UUID, workout.getId().toString());
        values.put(WorkoutDB.WorkoutTable.Cols.TITLE, workout.getFocus());
        values.put(WorkoutDB.WorkoutTable.Cols.DATE, workout.getDate().getTime());
        values.put(WorkoutDB.WorkoutTable.Cols.COMPLETED, workout.isCompleted() ? 1 : 0);

        return values;
    }
}
