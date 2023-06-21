package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.common.MultiSelectSpinner;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;

import java.util.List;

public class CreateCourseActivity extends AppCompatActivity {
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

        Button SubmitDataButton = findViewById(R.id.submitData);
        SubmitDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedCourses = multiSelectSpinner.getSelectedItems();

            }
        });
    }
}