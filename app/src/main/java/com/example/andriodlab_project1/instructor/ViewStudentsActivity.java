package com.example.andriodlab_project1.instructor;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.databinding.ActivityViewTheStudentsOfAnyCourseBinding;
import com.example.andriodlab_project1.enrollment.EnrollmentDataBaseHelper;
import com.example.andriodlab_project1.enrollment.ViewTheStudentsOfAnyCourseActivity;
import com.example.andriodlab_project1.student.Student;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewStudentsActivity extends AppCompatActivity {
    private TableLayout studentTable;


    ActivityViewTheStudentsOfAnyCourseBinding activityViewTheStudentsOfAnyCourseBinding;
    private TextView showStudents;
    private Course course;
    private AvailableCourseDataBaseHelper dbHelper;
    private CourseDataBaseHelper courseDataBaseHelper;
    private EnrollmentDataBaseHelper enrollmentDataBaseHelper;
    private StudentDataBaseHelper studentDataBaseHelper;
    CharSequence[] items;
    private List<Integer> continents;
    private int selected;
    private String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        //setContentView(R.layout.activity_view_the_students_of_any_course);
        showStudents = findViewById(R.id.newlistToSee);
        dbHelper = new AvailableCourseDataBaseHelper(this);
        courseDataBaseHelper = new CourseDataBaseHelper(this);
        enrollmentDataBaseHelper = new EnrollmentDataBaseHelper(this);
        studentDataBaseHelper = new StudentDataBaseHelper(this);
        continents = dbHelper.getAllCoursesForRegistrationAreTaughtByInstructor(MainActivity.instructorEmail);
        studentTable = findViewById(R.id.tableStudentInst);
        if (!(continents.isEmpty())) {
            showStudents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewStudentsActivity.this);
                    builder.setTitle("Courses :");
                    builder.setCancelable(false);

                    items = convertListToCharSequenceArray(continents);
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
                                Toast.makeText(ViewStudentsActivity.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                            } else {
                                course = courseDataBaseHelper.getCourseByID(continents.get(selected));
                                ArrayList<String> listOfEmail=enrollmentDataBaseHelper.getStudentsByCourseId(course.getCourseID());
                                List<Student> students=new ArrayList<>();
                                for (String s:listOfEmail) {
                                    students.add(studentDataBaseHelper.getStudentByEmail(s));
                                }
                                populateStudentTable(students);
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
        } else {
            Toast.makeText(ViewStudentsActivity.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();
        }
    }
    private void populateStudentTable(List<Student> students) {
        int rowCount = studentTable.getChildCount();

        for (int i = rowCount - 1; i > 0; i--) {
            View childView = studentTable.getChildAt(i);
            if (childView instanceof TableRow) {
                studentTable.removeView(childView);
            }
        }
        int count=0;
        for (Student student : students) {
            count++;
            TableRow row = new TableRow(this);
            TextView number = new TextView(this);
            TextView first = new TextView(this);
            TextView last = new TextView(this);
            TextView email = new TextView(this);
            number.setText(count+"");
            email.setText(student.getEmail());
            first.setText(student.getFirstName());
            last.setText(student.getLastName());
            row.addView(number);
            row.addView(first);
            row.addView(last);
            row.addView(email);
            studentTable.addView(row);
        }
    }
    public  CharSequence[] convertListToCharSequenceArray(List<Integer> list) {
        CharSequence[] array = new CharSequence[list.size()];
        int i = 0;
        for (Integer entry : list) {
            array[i++] = courseDataBaseHelper.getCourseName(entry);
        }
        return array;
    }
}