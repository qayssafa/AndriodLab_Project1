package com.example.andriodlab_project1.instructor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.InstructorDrawerBaswActivity;
import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.admin.AdminDataBaseHelper;
import com.example.andriodlab_project1.databinding.ActivityInstructorMainBinding;
import com.example.andriodlab_project1.student.SearchCoursesActivity;
import com.example.andriodlab_project1.student.StudentMainActivity;

public class InstructorMainActivity extends InstructorDrawerBaswActivity {

    private TextView InstructorName;
    private InstructorDataBaseHelper instructorDataBaseHelper;
    ActivityInstructorMainBinding activityInstructorMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInstructorMainBinding = ActivityInstructorMainBinding.inflate(getLayoutInflater());
        setContentView(activityInstructorMainBinding.getRoot());
        //setContentView(R.layout.activity_instructor_main);
        InstructorName = findViewById(R.id.incName);
        instructorDataBaseHelper = new InstructorDataBaseHelper(this);
        InstructorName.setText(instructorDataBaseHelper.getInstructorByEmail(MainActivity.instructorEmail).getFirstName() + " " + instructorDataBaseHelper.getInstructorByEmail(MainActivity.instructorEmail).getLastName());


    }
}
