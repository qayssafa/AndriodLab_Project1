package com.example.andriodlab_project1.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.common.User;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.databinding.ActivityAdminMainBinding;


public class AdminMainActivity extends DrawerBaseActivity {

    private TextView AdminName;
    private AdminDataBaseHelper adminDataBaseHelper;
    ActivityAdminMainBinding activityAdminMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminMainBinding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(activityAdminMainBinding.getRoot());
        AdminName = findViewById(R.id.AdminName);
        adminDataBaseHelper = new AdminDataBaseHelper(this);
        AdminName.setText(adminDataBaseHelper.getAdminByEmail(MainActivity.adminEmail).getFirstName() + " " + adminDataBaseHelper.getAdminByEmail(MainActivity.adminEmail).getLastName());
    }
}