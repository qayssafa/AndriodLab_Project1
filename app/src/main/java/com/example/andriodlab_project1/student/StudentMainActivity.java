package com.example.andriodlab_project1.student;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.admin.AdminMainActivity;
import com.example.andriodlab_project1.course.CreateCourseActivity;
import com.example.andriodlab_project1.course_for_registration.ViewPreviousOfferings;

public class StudentMainActivity extends AppCompatActivity {
    private Button notfiy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        notfiy=findViewById(R.id.notfiy);
        notfiy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StudentMainActivity.this, SearchAndViewCourseAreAvailable.class);
                startActivity(intent);
            }
        });
    }

}
