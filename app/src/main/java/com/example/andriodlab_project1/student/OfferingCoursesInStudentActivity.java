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
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.databinding.ActivityOfferingCoursesInStudentBinding;
import com.example.andriodlab_project1.databinding.ActivitySearchCoursesBinding;

import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class OfferingCoursesInStudentActivity  extends StudentDrawerBaseActivity {

    ActivityOfferingCoursesInStudentBinding activityOfferingCoursesInStudentBinding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOfferingCoursesInStudentBinding = ActivityOfferingCoursesInStudentBinding.inflate(getLayoutInflater());
        setContentView(activityOfferingCoursesInStudentBinding.getRoot());
        //setContentView(R.layout.activity_search_courses);
        TextView courseNumber;
        TextView courseTitle;
        TextView startTime;
        TextView instructorName;
        TableLayout tableLayout = findViewById(R.id.tableToSearchCourses);
        AvailableCourseDataBaseHelper availableCourseDataBaseHelper = new AvailableCourseDataBaseHelper(this);
        CourseDataBaseHelper dbHelper = new CourseDataBaseHelper(this);
        List<Map.Entry<String, String>> allCourses = availableCourseDataBaseHelper.getAllCoursesForRegistration();
        for (Map.Entry<String, String> courseInfo : allCourses) {
            int courseId = Integer.parseInt(courseInfo.getKey());
            List<Triple<AvailableCourse, String, Integer>> availableCourses = availableCourseDataBaseHelper.getAvailableCourseByCourse_Id(courseId);
            for (Triple<AvailableCourse, String, Integer> lCourseInfo : availableCourses) {
                AvailableCourse availableCourse = lCourseInfo.getFirst();
                String lInstructorName = lCourseInfo.getSecond();
                TableRow row = new TableRow(tableLayout.getContext());
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(1, 1, 1, 1);
                courseNumber = new TextView(row.getContext());
                courseTitle = new TextView(row.getContext());
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

                instructorName.setText(lInstructorName);
                instructorName.setBackgroundColor(Color.WHITE);
                instructorName.setLayoutParams(layoutParams);
                instructorName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(instructorName);

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

