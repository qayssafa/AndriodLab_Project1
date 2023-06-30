package com.example.andriodlab_project1.student;

import static com.example.andriodlab_project1.course.CreateCourseActivity.convertListToCharSequenceArray;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.enrollment.Enrollment;
import com.example.andriodlab_project1.enrollment.EnrollmentDataBaseHelper;
import com.example.andriodlab_project1.notification.NotificationDataBaseHelper;
import com.example.andriodlab_project1.signup.SignUPMainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class SearchAndViewCourseAreAvailable extends AppCompatActivity {

    private  Course course;
    private List<Map.Entry<String, String>> continents;
    private AvailableCourseDataBaseHelper dbHelper;
    private EnrollmentDataBaseHelper enrollmentDataBaseHelper;
    private NotificationDataBaseHelper notificationDataBaseHelper;
    private CourseDataBaseHelper courseDataBaseHelper;
    private int selected=0;
    private Map.Entry<String, String> entry;
    private String value;
    private TextView listOfCourses;
    private CharSequence[] items;
    private TextView courseNumber;
    private TextView courseTitle;
    private TextView courseMainTobic;
    private TextView preRequest;
    private Button enroll;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_and_view_courses);
        dbHelper=new AvailableCourseDataBaseHelper(this);
        courseDataBaseHelper=new CourseDataBaseHelper(this);
        enrollmentDataBaseHelper=new EnrollmentDataBaseHelper(this);
        notificationDataBaseHelper=new NotificationDataBaseHelper(this);
       /* courseNumber = findViewById(R.id.courseID);
        courseTitle = findViewById(R.id.courseTitle);
        courseMainTobic = findViewById(R.id.courseMainTobic);
        preRequest = findViewById(R.id.preRequest);*/
        listOfCourses = findViewById(R.id.listOfAvCourses);
        continents =dbHelper.getAllCoursesAreAvailableForRegistration();
        CharSequence[] items = convertListToCharSequenceArray(continents);
        enroll = findViewById(R.id.enroll);
        listOfCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchAndViewCourseAreAvailable.this);
                builder.setTitle("Courses Are Available For Registration :");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(items,selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = which;  // Update the selected continent index
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                      if (selected==0){
                            Toast.makeText(SearchAndViewCourseAreAvailable.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                        }else {
                          entry = continents.get(selected);
                          value = entry.getKey();
                          course=courseDataBaseHelper.getCourseByID(Integer.parseInt(value));
                      }
                        /*
                        courseNumber.setText(String.valueOf(course.getCourseID()));
                        courseTitle.setText(course.getCourseTitle());
                        courseMainTobic.setText(convertArrayListToString(course.getCourseMainTopics()));
                        preRequest.setText(convertArrayListToString(course.getPrerequisites()));
                        */
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });
        enroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<String> prerequisites = course.getPrerequisites();
                List<Map.Entry<Integer, String>> getCoursesTakenByStudent=enrollmentDataBaseHelper.getCoursesTakenByStudent(SignUPMainActivity.studentEmail);
                availableCourses=dbHelper.getAvailableCourseByCourse_Id(course.getCourseID());
                if (arePrerequisitesMet(prerequisites,getCoursesTakenByStudent)){
                    //SEND NOTIFY TO ADMIN
                    //wait untill recive respones
                    //no conflict in time
                    Enrollment enrollment=new Enrollment(course.getCourseID(),SignUPMainActivity.studentEmail);
                    enrollmentDataBaseHelper.insertStudent2Course(enrollment);
                    for (Triple<AvailableCourse, String, Integer> courseInfo : availableCourses) {
                        AvailableCourse availableCourse = courseInfo.getFirst();
                        String message="This Course "+CourseDataBaseHelper.getCourseName(availableCourse.getCourseId())+"Will be Starting in"+availableCourse.getCourseStartDate();
                        notificationDataBaseHelper.insertNotification(SignUPMainActivity.studentEmail,message);
                    }
                }
            }
        });
    }
    public boolean arePrerequisitesMet(List<String> prerequisites, List<Map.Entry<Integer, String>> getCoursesTakenByStudent) {
        List<String> courseNames = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : getCoursesTakenByStudent) {
            String courseName = entry.getValue();
            courseNames.add(courseName);
        }
        for (String prerequisite : prerequisites) {
            boolean prerequisiteMet = false;
            for (String courseTaken : courseNames) {
                if (prerequisite.equals(courseTaken)) {
                    prerequisiteMet = true;
                    break;
                }
            }
            if (!prerequisiteMet) {
                return false;
            }
        }
        return true;
    }

}
