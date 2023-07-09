package com.example.andriodlab_project1.student;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.databinding.ActivityCoursessStudiedBinding;

import java.util.List;
import java.util.Map;

import kotlin.Triple;


public class CoursessStudiedActivity extends StudentDrawerBaseActivity {

    ActivityCoursessStudiedBinding activityCoursessStudiedInTheCenterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCoursessStudiedInTheCenterBinding = ActivityCoursessStudiedBinding.inflate(getLayoutInflater());
        setContentView(activityCoursessStudiedInTheCenterBinding.getRoot());

        //setContentView(R.layout.activity_courses_studied_in_the_center);
        TextView courseNumber;
        TextView courseTitle;
        TextView schedule;
        TableLayout tableLayout = findViewById(R.id.tableForConductedCoursesNew);
        AvailableCourseDataBaseHelper availableCourseDataBaseHelper = new AvailableCourseDataBaseHelper(this);
        CourseDataBaseHelper dbHelper = new CourseDataBaseHelper(this);
        List<Map.Entry<Integer, String>> allCourses = availableCourseDataBaseHelper.getCoursesTakenByStudent(MainActivity.studentEmail);
        for (Map.Entry<Integer, String> courseId : allCourses) {
            List<Triple<AvailableCourse, String, Integer>> availableCourses = availableCourseDataBaseHelper.getAvailableCourseByCourse_Id(courseId.getKey());
            for (Triple<AvailableCourse, String, Integer> lCourseInfo : availableCourses) {
                AvailableCourse availableCourse = lCourseInfo.getFirst();
                TableRow row = new TableRow(tableLayout.getContext());
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(1, 1, 1, 1);

                courseNumber = new TextView(row.getContext());
                courseTitle = new TextView(row.getContext());
                schedule = new TextView(row.getContext());

                courseNumber.setText(String.valueOf(availableCourse.getCourseId()));
                courseNumber.setBackgroundColor(Color.WHITE);
                courseNumber.setLayoutParams(layoutParams);
                courseNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(courseNumber);

                Course course = dbHelper.getCourseByID(availableCourse.getCourseId());
                courseTitle.setText(course.getCourseTitle());
                courseTitle.setBackgroundColor(Color.WHITE);
                courseTitle.setLayoutParams(layoutParams);
                courseTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(courseTitle);

                schedule.setText(availableCourse.getCourseStartDate());
                schedule.setBackgroundColor(Color.WHITE);
                schedule.setLayoutParams(layoutParams);
                schedule.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(schedule);

                tableLayout.addView(row);
            }
        }
    }
}