package com.example.brr002.personaltrainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class WorkoutFragment extends Fragment{
    private Workout mWorkout;
    private EditText mFocusField;
    private Button mDateButton;
    private CheckBox mCompletedCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWorkout = new Workout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_workout, container, false);

        mFocusField = (EditText) v.findViewById(R.id.workout_focus);
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
        mDateButton.setText(mWorkout.getDate().toString());
        mDateButton.setEnabled(false);

        mCompletedCheckBox = (CheckBox) v.findViewById(R.id.workout_completed);
        mCompletedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mWorkout.setCompleted(isChecked);
            }
        });

        return v;
    }
}
