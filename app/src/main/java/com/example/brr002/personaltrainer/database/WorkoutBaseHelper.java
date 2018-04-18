package com.example.brr002.personaltrainer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.brr002.personaltrainer.database.WorkoutDB.WorkoutTable;

public class WorkoutBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "workoutBase.db";

    public WorkoutBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + WorkoutTable.NAME + "(" +
            " _id integer primary key autoincrement, " +
            WorkoutTable.Cols.UUID + ", " +
            WorkoutTable.Cols.TITLE + ", " +
            WorkoutTable.Cols.DATE + ", " +
            WorkoutTable.Cols.COMPLETED +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
