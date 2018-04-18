package com.example.brr002.personaltrainer.database;

public class WorkoutDB {
    public static final class WorkoutTable{
        public static final String NAME = "workouts";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String COMPLETED = "completed";
        }
    }
}
