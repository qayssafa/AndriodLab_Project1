package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.andriodlab_project1.course.CreateCourseActivity;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourse;
import com.example.andriodlab_project1.course.EditPage;
import com.example.andriodlab_project1.course.ViewTheStudentsOfAnyCourse;
import com.example.andriodlab_project1.course_for_registration.CourseForRegistrationActivity;

public class AdminMainActivity extends AppCompatActivity {
    private Button CreateCourseButton;
    private Button courseForRegestration;

    private Button editOrRemoveCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        CreateCourseButton = (Button)findViewById(R.id.CreateCourseButton);
        courseForRegestration = (Button)findViewById(R.id.courseForRegestration);
        editOrRemoveCourse = (Button)findViewById(R.id.editOrRemoveCourse);
        CreateCourseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AdminMainActivity.this, CreateCourseActivity.class);
                startActivity(intent);
            }
        });
        courseForRegestration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AdminMainActivity.this, CourseForRegistrationActivity.class);
                startActivity(intent);
            }
        });

        editOrRemoveCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AdminMainActivity.this, ViewTheStudentsOfAnyCourse.class);
                startActivity(intent);
            }
        });

    }
}