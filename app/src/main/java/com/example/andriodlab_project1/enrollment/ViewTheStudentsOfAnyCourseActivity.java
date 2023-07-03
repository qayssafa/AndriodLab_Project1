package com.example.andriodlab_project1.enrollment;

import static com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity.convertListToCharSequenceArray;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity;
import com.example.andriodlab_project1.course.EditPageActivity;
import com.example.andriodlab_project1.databinding.ActivityViewTheStudentsOfAnyCourseBinding;
import com.example.andriodlab_project1.student.Student;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewTheStudentsOfAnyCourseActivity extends DrawerBaseActivity {

    private TableLayout studentTable;


    ActivityViewTheStudentsOfAnyCourseBinding activityViewTheStudentsOfAnyCourseBinding;
    private TextView showStudents;
    private Course course;
    private CourseDataBaseHelper dbHelper;
    private EnrollmentDataBaseHelper enrollmentDataBaseHelper;
    private StudentDataBaseHelper studentDataBaseHelper;
    CharSequence[] items;
    private List<Map.Entry<String, String>> continents;
    private int selected;

    private Map.Entry<String, String> entry;
    private String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewTheStudentsOfAnyCourseBinding = ActivityViewTheStudentsOfAnyCourseBinding.inflate(getLayoutInflater());
        setContentView(activityViewTheStudentsOfAnyCourseBinding.getRoot());
        //setContentView(R.layout.activity_view_the_students_of_any_course);
        showStudents = findViewById(R.id.showStudents);
        dbHelper = new CourseDataBaseHelper(this);
        enrollmentDataBaseHelper = new EnrollmentDataBaseHelper(this);
        studentDataBaseHelper = new StudentDataBaseHelper(this);
        continents = dbHelper.getAllCourses();
        studentTable = findViewById(R.id.student_show_table);
        if (!(continents.isEmpty())) {
            showStudents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewTheStudentsOfAnyCourseActivity.this);
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
                                Toast.makeText(ViewTheStudentsOfAnyCourseActivity.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                            } else {
                                entry = continents.get(selected);
                                value = entry.getKey();
                                course = dbHelper.getCourseByID(Integer.parseInt(value));
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
            Toast.makeText(ViewTheStudentsOfAnyCourseActivity.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();
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
            TextView email = new TextView(this);
            TextView firstName = new TextView(this);
            TextView lastName = new TextView(this);
            number.setText(count+"");
            email.setText(student.getEmail());
            firstName.setText(student.getFirstName());
            lastName.setText(student.getLastName());
            row.addView(number);
            row.addView(email);
            row.addView(firstName);
            row.addView(lastName);
            studentTable.addView(row);
        }
    }
}