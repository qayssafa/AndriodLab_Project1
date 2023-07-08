package com.example.andriodlab_project1.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.databinding.ActivityCourseWithdrawBinding;

public class CourseWithdrawActivity extends StudentDrawerBaseActivity {

    private EditText ReasonsString;
    private Button WithdrawButton;

    ActivityCourseWithdrawBinding activityCourseWithdrawBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCourseWithdrawBinding = ActivityCourseWithdrawBinding.inflate(getLayoutInflater());
        setContentView(activityCourseWithdrawBinding.getRoot());
        //setContentView(R.layout.activity_course_withdraw);

        ReasonsString = (EditText) findViewById(R.id.Reasons);
        WithdrawButton = (Button) findViewById(R.id.SubmitWithdraw);
    }
}
