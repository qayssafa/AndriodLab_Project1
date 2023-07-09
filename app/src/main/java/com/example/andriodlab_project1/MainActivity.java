package com.example.andriodlab_project1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andriodlab_project1.admin.Admin;
import com.example.andriodlab_project1.admin.AdminDataBaseHelper;
import com.example.andriodlab_project1.admin.AdminMainActivity;
import com.example.andriodlab_project1.common.DataBaseHelper;
import com.example.andriodlab_project1.common.SharedPrefManager;
import com.example.andriodlab_project1.common.User;
import com.example.andriodlab_project1.instructor.InstructorMainActivity;
import com.example.andriodlab_project1.signup.SignUPMainActivity;
import com.example.andriodlab_project1.instructor.Instructor;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.student.Student;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;
import com.example.andriodlab_project1.student.StudentMainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private StudentDataBaseHelper studentDataBaseHelper;
    private AdminDataBaseHelper adminDataBaseHelper;
    private InstructorDataBaseHelper instructorDataBaseHelper;
    public static String studentEmail;
    public static String adminEmail;
    public static String instructorEmail;


    public static User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the data
        dataBaseHelper=new DataBaseHelper(this);
        adminDataBaseHelper = new AdminDataBaseHelper(this);
        studentDataBaseHelper = new StudentDataBaseHelper(this);
        instructorDataBaseHelper = new InstructorDataBaseHelper(this);
        Admin newUser = new Admin();
        Student student=new Student();
        Instructor instructor=new Instructor();
        newUser.setEmail("A");
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setPassword("1");
        adminDataBaseHelper.insertAdmin(newUser);
        student.setEmail("S");
        student.setFirstName("Joh1n");
        student.setLastName("Do1e");
        student.setPassword("1");
        student.setMobileNumber("123131323141323123");
        student.setAddress("sadaad");
        studentDataBaseHelper.insertStudent(student);
        instructor.setDegree("@13");
        instructor.setSpecialization("@13");
        instructor.setMobileNumber("@13");
        instructor.setPassword("1");
        instructor.setAddress("@13");
        instructor.setLastName("Safa");
        instructor.setFirstName("Qays");
        instructor.setEmail("I");
        List<String>courses=new ArrayList<>();
        courses.add("1");
        courses.add("2");
        courses.add("3");
        instructor.setCoursesTaught(courses);
        instructorDataBaseHelper.insertInstructor(instructor);

        // buttons and text views
        EditText email = findViewById(R.id.etEmail);
        EditText password = findViewById(R.id.etPassword);
        Button signIn = findViewById(R.id.btnSignIn);
        Button signUp = findViewById(R.id.btnSignUp);
        CheckBox rememberMe = findViewById(R.id.cbRememberMe);
        rememberMe.setChecked(true);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        if (!Objects.equals(sharedPrefManager.readString("email", "noValue"), "noValue")){
            email.setText(sharedPrefManager.readString("email", "noValue"));
        }
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = email.getText().toString().trim();
                String enterPassword = password.getText().toString().trim();
                if (!adminDataBaseHelper.isRegistered(enteredEmail) && !studentDataBaseHelper.isRegistered(enteredEmail) && !instructorDataBaseHelper.isRegistered(enteredEmail)) {
                    Toast.makeText(MainActivity.this, "This email is not registered!", Toast.LENGTH_SHORT).show();
                } else if (!adminDataBaseHelper.correctSignIn(enteredEmail, enterPassword) && !studentDataBaseHelper.correctSignIn(enteredEmail, enterPassword) && !instructorDataBaseHelper.correctSignIn(enteredEmail, enterPassword)) {
                    Toast.makeText(MainActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                } else {
                    if (rememberMe.isChecked())
                        sharedPrefManager.writeString("email", email.getText().toString().trim());
                    else {
                        sharedPrefManager.writeString("email", "noValue");
                    }
                    if (adminDataBaseHelper.isRegistered(enteredEmail)) {
                        adminEmail =enteredEmail;
                        user = adminDataBaseHelper.getAdminByEmail(email.getText().toString().trim());
                        startActivity(new Intent(MainActivity.this, AdminMainActivity.class));
                    } else if (studentDataBaseHelper.isRegistered(enteredEmail)) {
                        studentEmail=enteredEmail;
                        user = studentDataBaseHelper.getStudentByEmail(email.getText().toString().trim());
                        startActivity(new Intent(MainActivity.this, StudentMainActivity.class));
                    } else if (instructorDataBaseHelper.isRegistered(enteredEmail)) {
                        instructorEmail=enteredEmail;
                        user = instructorDataBaseHelper.getInstructorByEmail(email.getText().toString().trim());
                        startActivity(new Intent(MainActivity.this, InstructorMainActivity.class));
                    }
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUPMainActivity.class));
            }
        });
    }


}