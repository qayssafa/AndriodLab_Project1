package com.example.andriodlab_project1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.andriodlab_project1.admin.AdminMainActivity;
import com.example.andriodlab_project1.admin.ApplicantDecideActivity;
import com.example.andriodlab_project1.admin.ViewInstructorProfileActivity;
import com.example.andriodlab_project1.admin.ViewStudentProfileActivity;
import com.example.andriodlab_project1.course.CreateCourseActivity;
import com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity;
import com.example.andriodlab_project1.course_for_registration.EditCourseAvailableForRegistrationActivity;
import com.example.andriodlab_project1.course_for_registration.MakeCourseAvailableForRegistrationActivity;
import com.example.andriodlab_project1.course_for_registration.ViewPreviousOfferings;
import com.example.andriodlab_project1.enrollment.ViewTheStudentsOfAnyCourseActivity;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
       drawerLayout =(DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
         FrameLayout container = drawerLayout.findViewById(R.id.activitycontainer);
         container.addView(view);
            super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = drawerLayout.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int id = item.getItemId();

        if (id == R.id.home) {
            startActivity(new Intent(DrawerBaseActivity.this, AdminMainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.CNC) {
            startActivity(new Intent(DrawerBaseActivity.this, CreateCourseActivity.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.ED) {
            startActivity(new Intent(DrawerBaseActivity.this, EditOrDeleteAnExistingCourseActivity.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.MCA) {
            startActivity(new Intent(DrawerBaseActivity.this, MakeCourseAvailableForRegistrationActivity.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.ViewInstructorProfileActivity) {
            startActivity(new Intent(DrawerBaseActivity.this, ViewInstructorProfileActivity.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.ViewStudentProfileActivity) {
            startActivity(new Intent(DrawerBaseActivity.this, ViewStudentProfileActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.CH) {
            startActivity(new Intent(DrawerBaseActivity.this, ViewPreviousOfferings.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.EditCourseAvailableForRegistrationActivity) {
            startActivity(new Intent(DrawerBaseActivity.this, EditCourseAvailableForRegistrationActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.RD) {
                startActivity(new Intent(DrawerBaseActivity.this, ApplicantDecideActivity.class));
                overridePendingTransition(0, 0);
                finish();
        }else if (id == R.id.CS) {
            startActivity(new Intent(DrawerBaseActivity.this, ViewTheStudentsOfAnyCourseActivity.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.logout) {
            startActivity(new Intent(DrawerBaseActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }
        return false;
    }

    protected void allocateActivityTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }


}