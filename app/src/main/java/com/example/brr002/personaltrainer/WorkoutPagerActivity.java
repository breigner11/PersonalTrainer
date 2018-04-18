package com.example.brr002.personaltrainer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class WorkoutPagerActivity extends AppCompatActivity {
    private static final String EXTRA_WORKOUT_ID =
            "com.example.brr002.personaltrainer.workout_id";

    private ViewPager mViewPager;
    private List<Workout> mWorkouts;

    public static Intent newIntent(Context packageContext, UUID workoutId){
        Intent intent = new Intent(packageContext, WorkoutPagerActivity.class);
        intent.putExtra(EXTRA_WORKOUT_ID, workoutId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_pager);

        UUID workoutId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_WORKOUT_ID);

        mViewPager = (ViewPager) findViewById(R.id.workout_view_pager);

        mWorkouts = WorkoutGym.get(this).getWorkouts();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Workout workout = mWorkouts.get(position);
                return WorkoutFragment.newInstance(workout.getId());
            }

            @Override
            public int getCount() {
                return mWorkouts.size();
            }
        });

        for (int i = 0; i < mWorkouts.size(); i++){
            if (mWorkouts.get(i).getId().equals(workoutId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
