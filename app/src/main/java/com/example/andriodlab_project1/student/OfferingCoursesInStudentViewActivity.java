package com.example.andriodlab_project1.student;

import static com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity.convertArrayListToString;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;

import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class OfferingCoursesInStudentViewActivity  extends AppCompatActivity {
    private List<Map.Entry<String, String>> allCourses;
    private TableLayout tableLayout;

    private AvailableCourseDataBaseHelper availableCourseDataBaseHelper;
    private CourseDataBaseHelper dbHelper;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offering_course_in_student);
        TextView courseNumber;
        TextView courseTitle;
        TextView courseMainTopics;
        TextView preRequest;
        TextView startTime;
        TextView instructorName;
        tableLayout=findViewById(R.id.tableToSearchCourses);
        availableCourseDataBaseHelper = new AvailableCourseDataBaseHelper(this);
        dbHelper = new CourseDataBaseHelper(this);
        allCourses = availableCourseDataBaseHelper.getAllCoursesForRegistration();
        for (Map.Entry<String, String> courseInfo : allCourses) {
            int courseId = Integer.parseInt(courseInfo.getKey());
            availableCourses = availableCourseDataBaseHelper.getAvailableCourseByCourse_Id(courseId);
            for (Triple<AvailableCourse, String, Integer> lCourseInfo : availableCourses) {
                AvailableCourse availableCourse = lCourseInfo.getFirst();
                String linstructorName = lCourseInfo.getSecond();
                TableRow row = new TableRow(tableLayout.getContext());
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(1, 1, 1, 1);
                courseNumber = new TextView(row.getContext());
                courseTitle = new TextView(row.getContext());
                courseMainTopics = new TextView(row.getContext());
                preRequest = new TextView(row.getContext());
                startTime = new TextView(row.getContext());
                instructorName = new TextView(row.getContext());

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

                courseMainTopics.setText(convertArrayListToString(course.getCourseMainTopics()));
                courseMainTopics.setBackgroundColor(Color.WHITE);
                courseMainTopics.setLayoutParams(layoutParams);
                courseMainTopics.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(courseMainTopics);

                preRequest.setText(convertArrayListToString(course.getPrerequisites()));
                preRequest.setBackgroundColor(Color.WHITE);
                preRequest.setLayoutParams(layoutParams);
                preRequest.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                ;
                row.addView(preRequest);


                startTime.setText(availableCourse.getCourseStartDate());
                startTime.setBackgroundColor(Color.WHITE);
                startTime.setLayoutParams(layoutParams);
                startTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(startTime);

                instructorName.setText(linstructorName);
                instructorName.setBackgroundColor(Color.WHITE);
                instructorName.setLayoutParams(layoutParams);
                instructorName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(instructorName);
                tableLayout.addView(row);
            }
        }
    }
}

