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

import com.example.andriodlab_project1.student.CourseWithdrawActivity;
import com.example.andriodlab_project1.student.CoursesStudiedInTheCenterActivity;
import com.example.andriodlab_project1.student.OfferingCoursesInStudentActivity;
import com.example.andriodlab_project1.student.OfferingCoursesInStudentViewActivity;
import com.example.andriodlab_project1.student.SearchAndViewCourseAreAvailableActivity;
import com.example.andriodlab_project1.student.SearchCoursesActivity;
import com.example.andriodlab_project1.student.StudentMainActivity;
import com.example.andriodlab_project1.student.ViewEditProfile;
import com.example.andriodlab_project1.student.messages;
import com.google.android.material.navigation.NavigationView;

public class StudentDrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;


    @Override
    public void setContentView(View view) {
        drawerLayout =(DrawerLayout) getLayoutInflater().inflate(R.layout.activity_student_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activitycontainerStudent);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBarStudent);
        setSupportActionBar(toolbar);
        NavigationView navigationView = drawerLayout.findViewById(R.id.student_navigation_view);
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
            startActivity(new Intent(StudentDrawerBaseActivity.this, StudentMainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.Search_courses) {
            startActivity(new Intent(StudentDrawerBaseActivity.this, SearchCoursesActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.Messages) {
            startActivity(new Intent(StudentDrawerBaseActivity.this, messages.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.OfferingCourses) {
            startActivity(new Intent(StudentDrawerBaseActivity.this, OfferingCoursesInStudentActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.AvailableCourses) {
            startActivity(new Intent(StudentDrawerBaseActivity.this, SearchAndViewCourseAreAvailableActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.CoursesStudiedInTheCenter) {
            startActivity(new Intent(StudentDrawerBaseActivity.this, CoursesStudiedInTheCenterActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.CourseWithdraw) {
            startActivity(new Intent(StudentDrawerBaseActivity.this, CourseWithdrawActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.profile) {
            startActivity(new Intent(StudentDrawerBaseActivity.this, ViewEditProfile.class));
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.logout) {
            startActivity(new Intent(StudentDrawerBaseActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }
        return false;
    }
}