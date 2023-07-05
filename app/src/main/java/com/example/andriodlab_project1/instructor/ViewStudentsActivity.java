package com.example.andriodlab_project1.instructor;


import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.enrollment.EnrollmentDataBaseHelper;
import com.example.andriodlab_project1.student.Student;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentsActivity extends AppCompatActivity {
    private TableLayout studentTable;

    private Course course;
    private CourseDataBaseHelper courseDataBaseHelper;
    private EnrollmentDataBaseHelper enrollmentDataBaseHelper;
    private StudentDataBaseHelper studentDataBaseHelper;
    CharSequence[] items;
    private List<Integer> continents;
    private int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        TextView showStudents = findViewById(R.id.newlistToSee);
        AvailableCourseDataBaseHelper dbHelper = new AvailableCourseDataBaseHelper(this);
        courseDataBaseHelper = new CourseDataBaseHelper(this);
        enrollmentDataBaseHelper = new EnrollmentDataBaseHelper(this);
        studentDataBaseHelper = new StudentDataBaseHelper(this);
        continents = dbHelper.getAllCoursesForRegistrationAreTaughtByInstructor(MainActivity.instructorEmail);
        studentTable = findViewById(R.id.tableStudentInst);
        if (!(continents.isEmpty())) {
            showStudents.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewStudentsActivity.this);
                builder.setTitle("Courses :");
                builder.setCancelable(false);

                items = convertListToCharSequenceArray(continents);
                builder.setSingleChoiceItems(items, selected, (dialog, which) -> {
                    selected = which;  // Update the selected continent index
                });
                builder.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    if (selected < 0) {
                        Toast.makeText(ViewStudentsActivity.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                    } else {
                        course = courseDataBaseHelper.getCourseByID(continents.get(selected));
                        ArrayList<String> listOfEmail = enrollmentDataBaseHelper.getStudentsByCourseId(course.getCourseID());
                        List<Student> students = new ArrayList<>();
                        for (String s : listOfEmail) {
                            students.add(studentDataBaseHelper.getStudentByEmail(s));
                        }
                        populateStudentTable(students);
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                builder.show();
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
        int count = 0;
        for (Student student : students) {
            count++;
            TableRow row = new TableRow(this);
            TextView number = new TextView(this);
            TextView first = new TextView(this);
            TextView last = new TextView(this);
            TextView email = new TextView(this);
            number.setText(String.valueOf(count ));
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

    public CharSequence[] convertListToCharSequenceArray(List<Integer> list) {
        CharSequence[] array = new CharSequence[list.size()];
        int i = 0;
        for (Integer entry : list) {
            array[i++] = courseDataBaseHelper.getCourseName(entry);
        }
        return array;
    }
}