package com.example.andriodlab_project1.course;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.student.Student;

import java.util.ArrayList;
import java.util.List;

public class ViewTheStudentsOfAnyCourse extends AppCompatActivity {

    private TableLayout studentTable;

    private List<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_the_students_of_any_course);

        studentTable = findViewById(R.id.student_table);



        students.add(new Student("john.doe@example.com", "John", "Doe", "123456789", "dfdfd", "dsdd"));

        populateStudentTable();
    }

    private void populateStudentTable() {
        studentTable.removeAllViews();

        for (Student student : students) {
            TableRow row = new TableRow(this);
            TextView email = new TextView(this);
            TextView firstName = new TextView(this);
            TextView lastName = new TextView(this);

            email.setText(student.getEmail());
            firstName.setText(student.getFirstName());
            lastName.setText(student.getLastName());

            row.addView(email);
            row.addView(firstName);
            row.addView(lastName);

            studentTable.addView(row);
        }
    }
}