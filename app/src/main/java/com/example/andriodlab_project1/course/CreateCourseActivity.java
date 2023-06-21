package com.example.andriodlab_project1.course;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.common.DataBaseHelper;
import com.example.andriodlab_project1.common.MultiSelectSpinner;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;

import java.util.List;

public class CreateCourseActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private CourseDataBaseHelper dbHelper;
    private MultiSelectSpinner multiSelectSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        dbHelper = new CourseDataBaseHelper(this);
        List<String> courses = dbHelper.getAllCourses();
        String[] coursesArray = courses.toArray(new String[0]);

        multiSelectSpinner = new MultiSelectSpinner(this, coursesArray);
        Button btnShowSpinner = findViewById(R.id.PrerequisitesInput);
        btnShowSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectSpinner.showDialog();
            }
        });
    }
}