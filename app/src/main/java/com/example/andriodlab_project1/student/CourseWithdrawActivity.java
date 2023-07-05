package com.example.andriodlab_project1.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.andriodlab_project1.R;

public class CourseWithdrawActivity extends AppCompatActivity {

    private EditText ReasonsString;
    private Button WithdrawButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_withdraw);

        ReasonsString = (EditText) findViewById(R.id.Reasons);
        WithdrawButton = (Button) findViewById(R.id.SubmitWithdraw);
    }
}
