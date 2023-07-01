package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.common.User;
import com.example.andriodlab_project1.course.CreateCourseActivity;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity;
import com.example.andriodlab_project1.course_for_registration.MakeCourseAvailableForRegistrationActivity;
import com.example.andriodlab_project1.course_for_registration.ViewPreviousOfferings;
import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.databinding.ActivityAdminMainBinding;


public class AdminMainActivity extends DrawerBaseActivity {
    private Button CreateCourseButton;
    private Button courseForRegestration;

    private Button editOrRemoveCourse;
    private Button viewOffering;
    private TextView AdminName;
    private AdminDataBaseHelper adminDataBaseHelper;
    public static User user = new User();


    ActivityAdminMainBinding activityAdminMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminMainBinding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(activityAdminMainBinding.getRoot());
        AdminName = (TextView)findViewById(R.id.AdminName);
        adminDataBaseHelper = new AdminDataBaseHelper(this);
        AdminName.setText(adminDataBaseHelper.getAdminByEmail(MainActivity.AdminEmail).getFirstName()+" "+adminDataBaseHelper.getAdminByEmail(MainActivity.AdminEmail).getLastName());
        /*CreateCourseButton = (Button)findViewById(R.id.CreateCourseButton);
        courseForRegestration = (Button)findViewById(R.id.courseForRegestration);
        editOrRemoveCourse = (Button)findViewById(R.id.editOrRemoveCourse);
        viewOffering = (Button)findViewById(R.id.viewOffering);
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
                Intent intent = new Intent(AdminMainActivity.this, MakeCourseAvailableForRegistrationActivity.class);
                startActivity(intent);
            }
        });

        editOrRemoveCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AdminMainActivity.this, EditOrDeleteAnExistingCourseActivity.class);
                startActivity(intent);
            }
        });
        viewOffering.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AdminMainActivity.this, ViewPreviousOfferings.class);
                startActivity(intent);
            }
        });*/

    }
}