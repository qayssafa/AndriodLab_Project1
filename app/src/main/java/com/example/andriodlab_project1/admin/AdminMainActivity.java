package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.andriodlab_project1.course.CreateCourseActivity;

import com.example.andriodlab_project1.R;

public class AdminMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Button CreateCourseButton = (Button)findViewById(R.id.CreateCourseButton);
        CreateCourseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AdminMainActivity.this, CreateCourseActivity.class);
                startActivity(intent);
            }
        });

    }
}