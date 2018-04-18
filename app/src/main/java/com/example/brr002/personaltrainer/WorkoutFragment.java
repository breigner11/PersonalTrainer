package com.example.brr002.personaltrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

public class WorkoutFragment extends Fragment{

    private static final String ARG_WORKOUT_ID = "workout_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Workout mWorkout;
    private EditText mFocusField;
    private Button mDateButton;
    private CheckBox mCompletedCheckBox;

    public static WorkoutFragment newInstance(UUID workoutId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_WORKOUT_ID, workoutId);

        WorkoutFragment fragment = new WorkoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID workoutId = (UUID) getArguments().getSerializable(ARG_WORKOUT_ID);
        mWorkout = WorkoutGym.get(getActivity()).getWorkout(workoutId);
    }

    @Override
    public void onPause(){
        super.onPause();

        WorkoutGym.get(getActivity())
                .updateWorkout(mWorkout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_workout, container, false);

        mFocusField = (EditText) v.findViewById(R.id.workout_focus);
        mFocusField.setText(mWorkout.getFocus());
        mFocusField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after){
                //intentionally blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count){
                mWorkout.setFocus(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s){
                //intentionally blank
            }
        });

        mDateButton = (Button) v.findViewById(R.id.workout_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mWorkout.getDate());
                dialog.setTargetFragment(WorkoutFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mCompletedCheckBox = (CheckBox) v.findViewById(R.id.workout_completed);
        mCompletedCheckBox.setChecked(mWorkout.isCompleted());
        mCompletedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mWorkout.setCompleted(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_DATE){
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mWorkout.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mWorkout.getDate().toString());
    }
}
