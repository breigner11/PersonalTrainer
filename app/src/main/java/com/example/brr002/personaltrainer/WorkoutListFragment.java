package com.example.brr002.personaltrainer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WorkoutListFragment extends Fragment {

    private RecyclerView mWorkoutRecyclerView;
    private WorkoutAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_workout_list, container, false);

        mWorkoutRecyclerView = (RecyclerView) view
                .findViewById(R.id.workout_recycler_view);
        mWorkoutRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){
        WorkoutGym workoutGym = WorkoutGym.get(getActivity());
        List<Workout> workouts = workoutGym.getWorkouts();

        mAdapter = new WorkoutAdapter(workouts);
        mWorkoutRecyclerView.setAdapter(mAdapter);
    }

    private class WorkoutHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private Workout mWorkout;
        private TextView mFocusTextView;
        private TextView mDateTextView;

        public WorkoutHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_workout, parent, false));
            itemView.setOnClickListener(this);

            mFocusTextView = (TextView) itemView.findViewById(R.id.workout_focus);
            mDateTextView = (TextView) itemView.findViewById(R.id.workout_date);
        }

        public void bind(Workout workout){
            mWorkout = workout;
            mFocusTextView.setText(mWorkout.getFocus());
            mDateTextView.setText(mWorkout.getDate().toString());
        }
    }

    private class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder>{

        private List<Workout> mWorkouts;

        public WorkoutAdapter(List<Workout> workouts){
            mWorkouts = workouts;
        }

        @Override
        public WorkoutHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new WorkoutHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(WorkoutHolder holder, int position){
            Workout workout = mWorkouts.get(position);
            holder.bind(workout);
        }

        @Override
        public int getItemCount(){
            return mWorkouts.size();
        }

        @Override
        public void onClick(View view){
            Toast.makeText(getActivity(),
                    mWorkout.getFocus() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
