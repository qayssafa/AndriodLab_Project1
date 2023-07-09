package com.example.andriodlab_project1.instructor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.andriodlab_project1.InstructorDrawerBaswActivity;
import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.databinding.ActivityCourcesPreviouslyTaughtBinding;

import java.util.List;

import kotlin.Triple;

public class CoursesPreviouslyTaughtActivity extends InstructorDrawerBaswActivity {

    ActivityCourcesPreviouslyTaughtBinding activityCourcesPreviouslyTaughtBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCourcesPreviouslyTaughtBinding = ActivityCourcesPreviouslyTaughtBinding.inflate(getLayoutInflater());
        setContentView(activityCourcesPreviouslyTaughtBinding.getRoot());
        //setContentView(R.layout.activity_cources_previously_taught);

        TextView courseNumber;
        TextView courseTitle;
        TextView startTime;
        TableLayout tableLayout = findViewById(R.id.tablePreviosly);
        AvailableCourseDataBaseHelper availableCourseDataBaseHelper = new AvailableCourseDataBaseHelper(this);
        CourseDataBaseHelper dbHelper = new CourseDataBaseHelper(this);
        List<Integer> allCourses = availableCourseDataBaseHelper.getAllCoursesForRegistrationAreTaughtByInstructor(MainActivity.instructorEmail);
        for (Integer courseId : allCourses) {
            List<Triple<AvailableCourse, String, Integer>> availableCourses = availableCourseDataBaseHelper.getAvailableCourseByCourse_Id(courseId);
            for (Triple<AvailableCourse, String, Integer> lCourseInfo : availableCourses) {
                AvailableCourse availableCourse = lCourseInfo.getFirst();
                TableRow row = new TableRow(tableLayout.getContext());
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(1, 1, 1, 1);
                courseNumber = new TextView(row.getContext());
                courseTitle = new TextView(row.getContext());
                startTime = new TextView(row.getContext());

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


                startTime.setText(availableCourse.getCourseStartDate());
                startTime.setBackgroundColor(Color.WHITE);
                startTime.setLayoutParams(layoutParams);
                startTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(startTime);

                tableLayout.addView(row);
            }
        }
    }
}