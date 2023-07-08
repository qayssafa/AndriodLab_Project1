package com.example.andriodlab_project1.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.databinding.ActivityCoursesStudiedInTheCenterBinding;

public class CoursesStudiedInTheCenterActivity extends StudentDrawerBaseActivity {

    ActivityCoursesStudiedInTheCenterBinding activityCoursesStudiedInTheCenterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCoursesStudiedInTheCenterBinding = ActivityCoursesStudiedInTheCenterBinding.inflate(getLayoutInflater());
        setContentView(activityCoursesStudiedInTheCenterBinding.getRoot());
        //setContentView(R.layout.activity_courses_studied_in_the_center);
    }
}