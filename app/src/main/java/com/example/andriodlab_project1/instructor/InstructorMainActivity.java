package com.example.andriodlab_project1.instructor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.student.SearchCoursesActivity;
import com.example.andriodlab_project1.student.StudentMainActivity;

public class InstructorMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_main);
        Button button=findViewById(R.id.button4);
        Button button1=findViewById(R.id.button5);
        Button button2=findViewById(R.id.button6);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(InstructorMainActivity.this, CoursesPreviouslyTaughtActivity.class);
            startActivity(intent);
        });

        button1.setOnClickListener(v -> {
            Intent intent = new Intent(InstructorMainActivity.this, CurrentScheduleActivity.class);
            startActivity(intent);
        });
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(InstructorMainActivity.this, ViewStudentsActivity.class);
            startActivity(intent);
        });
    }
}
