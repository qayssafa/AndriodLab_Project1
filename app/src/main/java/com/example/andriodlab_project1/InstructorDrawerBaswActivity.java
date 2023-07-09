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

import com.example.andriodlab_project1.instructor.CoursesPreviouslyTaughtActivity;
import com.example.andriodlab_project1.instructor.CurrentScheduleActivity;
import com.example.andriodlab_project1.instructor.InstructorMainActivity;
import com.example.andriodlab_project1.instructor.ViewStudentsActivity;
import com.example.andriodlab_project1.student.SearchAndViewCourseAreAvailableActivity;
import com.example.andriodlab_project1.student.SearchCoursesActivity;
import com.example.andriodlab_project1.student.StudentMainActivity;
import com.example.andriodlab_project1.student.ViewEditProfile;
import com.google.android.material.navigation.NavigationView;

public class InstructorDrawerBaswActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;


    @Override
    public void setContentView(View view) {
        drawerLayout =(DrawerLayout) getLayoutInflater().inflate(R.layout.activity_instructor_drawer_basw, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activitycontainerInstructor);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBarInstructor);
        setSupportActionBar(toolbar);
        NavigationView navigationView = drawerLayout.findViewById(R.id.Instructor_navigation_view);
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
            startActivity(new Intent(InstructorDrawerBaswActivity.this, InstructorMainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.CoursesPreviouslyTaughtActivity) {
            startActivity(new Intent(InstructorDrawerBaswActivity.this, CoursesPreviouslyTaughtActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.CurrentSchedule) {
            startActivity(new Intent(InstructorDrawerBaswActivity.this, CurrentScheduleActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.ViewStudentsActivity) {
            startActivity(new Intent(InstructorDrawerBaswActivity.this, ViewStudentsActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.profile) {
            startActivity(new Intent(InstructorDrawerBaswActivity.this, ViewEditProfile.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.logout) {
            startActivity(new Intent(InstructorDrawerBaswActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }
        return false;
    }
}