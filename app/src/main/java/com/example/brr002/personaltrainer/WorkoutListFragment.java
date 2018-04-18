package com.example.brr002.personaltrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WorkoutListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mWorkoutRecyclerView;
    private WorkoutAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_list, container, false);

        mWorkoutRecyclerView = (RecyclerView) view
                .findViewById(R.id.workout_recycler_view);
        mWorkoutRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_workout_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.new_workout:
                Workout workout = new Workout();
                WorkoutGym.get(getActivity()).addWorkout(workout);
                Intent intent = WorkoutPagerActivity
                        .newIntent(getActivity(), workout.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle(){
        WorkoutGym workoutGym = WorkoutGym.get(getActivity());
        int workoutCount = workoutGym.getWorkouts().size();
        String subtitle = getString(R.string.subtitle_format, workoutCount);

        if (!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        WorkoutGym workoutGym = WorkoutGym.get(getActivity());
        List<Workout> workouts = workoutGym.getWorkouts();

        if (mAdapter == null) {
            mAdapter = new WorkoutAdapter(workouts);
            mWorkoutRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setWorkouts(workouts);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class WorkoutHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Workout mWorkout;
        private TextView mFocusTextView;
        private TextView mDateTextView;
        private ImageView mCompletedImageView;

        public WorkoutHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_workout, parent, false));
            itemView.setOnClickListener(this);

            mFocusTextView = (TextView) itemView.findViewById(R.id.workout_focus);
            mDateTextView = (TextView) itemView.findViewById(R.id.workout_date);
            mCompletedImageView = (ImageView) itemView.findViewById(R.id.workout_completed);
        }

        public void bind(Workout workout) {
            mWorkout = workout;
            mFocusTextView.setText(mWorkout.getFocus());
            mDateTextView.setText(mWorkout.getDate().toString());
            mCompletedImageView.setVisibility(workout.isCompleted() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            Intent intent = WorkoutPagerActivity.newIntent(getActivity(), mWorkout.getId());
            startActivity(intent);
        }
    }

    private class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder> {

        private List<Workout> mWorkouts;

        public WorkoutAdapter(List<Workout> workouts) {
            mWorkouts = workouts;
        }

        @Override
        public WorkoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new WorkoutHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(WorkoutHolder holder, int position) {
            Workout workout = mWorkouts.get(position);
            holder.bind(workout);
        }

        @Override
        public int getItemCount() {
            return mWorkouts.size();
        }

        public void setWorkouts(List<Workout> workouts){
            mWorkouts = workouts;
        }
    }
}