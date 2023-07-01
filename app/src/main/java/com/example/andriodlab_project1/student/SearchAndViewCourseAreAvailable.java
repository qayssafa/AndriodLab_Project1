package com.example.andriodlab_project1.student;

import static com.example.andriodlab_project1.course.CreateCourseActivity.convertListToCharSequenceArray;

import android.content.DialogInterface;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.admin.Applicant;
import com.example.andriodlab_project1.admin.ApplicantDataBaseHelper;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.ViewPreviousOfferings;
import com.example.andriodlab_project1.enrollment.Enrollment;
import com.example.andriodlab_project1.enrollment.EnrollmentDataBaseHelper;
import com.example.andriodlab_project1.notification.NotificationDataBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class SearchAndViewCourseAreAvailable extends AppCompatActivity {

    private  Course course;
    private TableLayout tableLayout;

    private List<Map.Entry<String, String>> continents;
    private AvailableCourseDataBaseHelper dbHelper;
    private ApplicantDataBaseHelper applicantDataBaseHelper;
    private EnrollmentDataBaseHelper enrollmentDataBaseHelper;
    private NotificationDataBaseHelper notificationDataBaseHelper;
    private CourseDataBaseHelper courseDataBaseHelper;
    private int selected;
    private Map.Entry<String, String> entry;
    private String key;
    private TextView listOfCourses;
    private CharSequence[] items;
    private TextView courseNumber;
    private TextView courseTitle;
    private TextView courseMainTopic;
    private TextView time;
    private Button enroll;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_course);
        dbHelper=new AvailableCourseDataBaseHelper(this);
        applicantDataBaseHelper=new ApplicantDataBaseHelper(this);
        courseDataBaseHelper=new CourseDataBaseHelper(this);
        enrollmentDataBaseHelper=new EnrollmentDataBaseHelper(this);
        notificationDataBaseHelper=new NotificationDataBaseHelper(this);
        listOfCourses = findViewById(R.id.enrollCoursesList);
        continents =dbHelper.getAllCoursesAreAvailableForRegistration();
        CharSequence[] items = convertListToCharSequenceArray(continents);
        enroll = findViewById(R.id.ENrollButton);

        if (!continents.isEmpty()) {
        listOfCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchAndViewCourseAreAvailable.this);
                builder.setTitle("Courses Are Available For Registration :");
                builder.setCancelable(false);
                tableLayout = findViewById(R.id.student_message_table);
                builder.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = which;  // Update the selected continent index
                    }
                });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (selected < 0) {
                                Toast.makeText(SearchAndViewCourseAreAvailable.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                            } else {
                                entry = continents.get(selected);
                                key = entry.getKey();
                                course = courseDataBaseHelper.getCourseByID(Integer.parseInt(key));

                                TableRow row = new TableRow(tableLayout.getContext());
                                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(1, 1, 1, 1);
                                courseNumber = new TextView(row.getContext());
                                courseTitle = new TextView(row.getContext());
                                time = new TextView(row.getContext());
                                courseMainTopic = new TextView(row.getContext());

                                courseNumber.setText(course.getCourseID() + "");
                                courseNumber.setBackgroundColor(Color.WHITE);
                                courseNumber.setLayoutParams(layoutParams);
                                courseNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(courseNumber);

                                courseTitle.setText(course.getCourseTitle());
                                courseTitle.setBackgroundColor(Color.WHITE);
                                courseTitle.setLayoutParams(layoutParams);
                                courseTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(courseTitle);

                                time.setText(entry.getValue());
                                time.setBackgroundColor(Color.WHITE);
                                time.setLayoutParams(layoutParams);
                                time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(time);

                                courseMainTopic.setText(convertArrayListToString(course.getCourseMainTopics()));
                                courseMainTopic.setBackgroundColor(Color.WHITE);
                                courseMainTopic.setLayoutParams(layoutParams);
                                courseMainTopic.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(courseMainTopic);

                                tableLayout.addView(row);
                            }
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
        }
        else {
            Toast.makeText(SearchAndViewCourseAreAvailable.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();

        }
        enroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<String> prerequisites = course.getPrerequisites();
                List<Map.Entry<Integer, String>> getCoursesTakenByStudent=dbHelper.getCoursesTakenByStudent(MainActivity.studentEmail);
                if (arePrerequisitesMet(prerequisites,getCoursesTakenByStudent)){
                    Applicant applicant=new Applicant(course.getCourseID(),MainActivity.studentEmail,"NO");
                    applicantDataBaseHelper.insertApplicant(applicant);
                    Toast.makeText(SearchAndViewCourseAreAvailable.this,"Wait Until Admin Accept Your Request.", Toast.LENGTH_SHORT).show();
                    //no conflict in time
                }
            }
        });
    }
    public static String convertArrayListToString(ArrayList<String> arrayList) {
        return String.join("\n", arrayList);
    }
    public CharSequence[] convertListToCharSequenceArray(List<Map.Entry<String, String>> list) {
        CharSequence[] array = new CharSequence[list.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : list) {
            array[i++] = CourseDataBaseHelper.getCourseName(Integer.parseInt(entry.getKey()));
        }
        return array;
    }
    public boolean arePrerequisitesMet(List<String> prerequisites, List<Map.Entry<Integer, String>> getCoursesTakenByStudent) {
        if (prerequisites.isEmpty()|| prerequisites.get(0).isBlank()){
            return true;
        }
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
