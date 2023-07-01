package com.example.andriodlab_project1.student;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.R;

public class StudentMainActivity extends AppCompatActivity {
    private Button enroll;
    private Button messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        enroll=findViewById(R.id.enrollButtonAndSee);
        messages=findViewById(R.id.messages);
        enroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StudentMainActivity.this, SearchAndViewCourseAreAvailable.class);
                startActivity(intent);
            }
        });
        messages.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StudentMainActivity.this, messages.class);
                startActivity(intent);
            }
        });
    }

}
