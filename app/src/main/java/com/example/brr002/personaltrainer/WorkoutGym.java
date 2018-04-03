package com.example.brr002.personaltrainer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkoutGym {
    private static WorkoutGym sWorkoutGym;

    private List<Workout> mWorkouts;

    public static WorkoutGym get(Context context){
        if (sWorkoutGym == null){
            sWorkoutGym = new WorkoutGym(context);
        }
        return sWorkoutGym;
    }

    private WorkoutGym(Context context){
        mWorkouts = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            Workout workout = new Workout();
            workout.setFocus("Workout #" + i);
            workout.setCompleted(i % 2 == 0);
            mWorkouts.add(workout);
        }
    }

    public List<Workout> getWorkouts(){
        return mWorkouts;
    }

    public Workout getWorkout(UUID id){
        for (Workout workout : mWorkouts){
            if (workout.getId().equals(id)){
                return workout;
            }
        }
        return null;
    }
}
