package com.example.andriodlab_project1.course_for_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andriodlab_project1.R;

public class EditCourseAvailabeForRegistrationActivity extends AppCompatActivity {

    private EditText InstructorName;
    private EditText CourseStartDate;
    private EditText CourseEndDate;
    private EditText RegistrationDeadline;
    private EditText CourseSchedule;
    private EditText Venue;
    private TextView CourseNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_availabe_for_registration);

        InstructorName = (EditText) findViewById(R.id.InstructorNameEditText);
        CourseStartDate = (EditText) findViewById(R.id.StartDateEditText);
        CourseEndDate = (EditText) findViewById(R.id.CourseEndDate);
        RegistrationDeadline = (EditText) findViewById(R.id.RegistrationDeadLineEditText);
        CourseSchedule = (EditText) findViewById(R.id.CourseScheduleEditText);
        Venue = (EditText) findViewById(R.id.VenueEditText);

        CourseNumber = (TextView) findViewById(R.id.CourseNumberTextView);
        CourseNumber.setText(View.TEXT_ALIGNMENT_CENTER);
        CourseNumber.setTextSize(20);
        CourseNumber.setTextColor(Color.BLACK);
        CourseNumber.setTypeface(Typeface.DEFAULT_BOLD);
    }
}