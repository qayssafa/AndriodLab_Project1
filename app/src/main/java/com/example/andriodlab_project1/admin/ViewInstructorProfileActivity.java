package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.instructor.Instructor;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.student.Student;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

public class ViewInstructorProfileActivity extends AppCompatActivity {

    TextView instructorFName;
    TextView instructorLName;
    EditText instructorEmail;
    TextView instructorPhone;
    TextView instructorAddress;
    TextView instructorSpecialization;
    TextView instructorDegree;
    ImageButton searchInstructor;
    private InstructorDataBaseHelper instructorDataBaseHelper;
    private StudentDataBaseHelper studentDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instructor_profile);
        instructorFName = findViewById(R.id.InsFirstName);
        instructorLName = findViewById(R.id.InsLastName);
        instructorEmail = findViewById(R.id.enterEmail);
        instructorPhone = findViewById(R.id.InsMobileNumber);
        instructorAddress = findViewById(R.id.InsAddress);
        instructorSpecialization = findViewById(R.id.InsSpecialization);
        instructorDegree = findViewById(R.id.InsDegree);
        searchInstructor = findViewById(R.id.searchInstructor);
        instructorDataBaseHelper = new InstructorDataBaseHelper(this);
        studentDataBaseHelper = new StudentDataBaseHelper(this);


        searchInstructor.setOnClickListener(v -> {
            if (!(instructorEmail.getText().toString().isEmpty()) && studentDataBaseHelper.isRegistered(instructorEmail.getText().toString())) {
                Student student1 = studentDataBaseHelper.getStudentByEmail(instructorEmail.getText().toString());
            } else if (!(instructorEmail.getText().toString().isEmpty()) && instructorDataBaseHelper.isRegistered(instructorEmail.getText().toString())) {

                Instructor instructor1 = instructorDataBaseHelper.getInstructorByEmail(instructorEmail.getText().toString());

                instructorLName.setText(String.valueOf(instructor1.getLastName()));
                instructorFName.setText(String.valueOf(instructor1.getFirstName()));
                instructorPhone.setText(String.valueOf(instructor1.getMobileNumber()));
                instructorAddress.setText(String.valueOf(instructor1.getAddress()));
                instructorSpecialization.setText(String.valueOf(instructor1.getSpecialization()));
                instructorDegree.setText(String.valueOf(instructor1.getDegree()));

            } else {
                Toast.makeText(ViewInstructorProfileActivity.this, "This Email its not Valid.", Toast.LENGTH_SHORT).show();

            }
        });


    }

}