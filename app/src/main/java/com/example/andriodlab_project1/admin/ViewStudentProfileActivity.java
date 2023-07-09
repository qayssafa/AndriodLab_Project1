package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.databinding.ActivityViewStudentProfileBinding;

public class ViewStudentProfileActivity extends DrawerBaseActivity {

    ActivityViewStudentProfileBinding activityViewStudentProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewStudentProfileBinding = ActivityViewStudentProfileBinding.inflate(getLayoutInflater());
        setContentView(activityViewStudentProfileBinding.getRoot());
        //setContentView(R.layout.activity_view_student_profile);
    }
}