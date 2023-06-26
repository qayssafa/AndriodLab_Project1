package com.example.andriodlab_project1.signup;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.admin.Admin;
import com.example.andriodlab_project1.admin.AdminDataBaseHelper;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.instructor.Instructor;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.student.Student;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUPMainActivity extends AppCompatActivity {

    private StudentDataBaseHelper dbHelperStudent;
    private InstructorDataBaseHelper dbHelperInstructor;
    private AdminDataBaseHelper dbHelperAdmin;
    private CourseDataBaseHelper dbHelperCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText firstName = findViewById(R.id.editFirstName);
        EditText lastName = findViewById(R.id.editLastName);
        EditText email = findViewById(R.id.editEmail);
        EditText password = findViewById(R.id.editPassword);
        EditText confirmPassword = findViewById(R.id.editConfirmPassword);
        Button signUpInstructor = findViewById(R.id.btnSignUpInstructor);
        Button signUpStudent = findViewById(R.id.btnSignUpStudent);
        Button signUpAdmin = findViewById(R.id.btnSignUpAdmin);
        Button signUp = findViewById(R.id.signup);
        dbHelperStudent = new StudentDataBaseHelper(this);
        dbHelperInstructor = new InstructorDataBaseHelper(this);
        dbHelperAdmin = new AdminDataBaseHelper(this);
        dbHelperCourse = new CourseDataBaseHelper(this);
        final instructorFragment instructor = new instructorFragment();
        final StudentFragment student = new StudentFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        signUpAdmin.setEnabled(false);
        signUpInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(student);
                fragmentTransaction.add(R.id.root_layout, instructor, "instructor");
                fragmentTransaction.commit();
                signUpInstructor.setEnabled(false);
                signUpStudent.setEnabled(true);
                signUpAdmin.setEnabled(true);
            }
        });
        signUpAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(instructor);
                fragmentTransaction.remove(student);
                fragmentTransaction.commit();
                signUpInstructor.setEnabled(true);
                signUpStudent.setEnabled(true);
                signUpAdmin.setEnabled(false);
            }
        });
        signUpStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(instructor);
                fragmentTransaction.add(R.id.root_layout, student, "student");
                fragmentTransaction.commit();
                signUpInstructor.setEnabled(true);
                signUpStudent.setEnabled(false);
                signUpAdmin.setEnabled(true);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  lFirstName= firstName.getText().toString();
                String  lLastName=lastName.getText().toString();
                String lEmail=email.getText().toString();
                String lConfirmPassword=confirmPassword.getText().toString();
                String lPassword=password.getText().toString();
                String lSpecialization;
                boolean checkResult;
                String lAddress;
                String lListOfCourses;
                String lPhone;
                boolean bsc;
                boolean msc;
                boolean phd;
                Instructor instructor1;
                Admin admin;
                Student student1;
                if (!signUpStudent.isEnabled()) {
                    lAddress=student.getAddressStudentValue();
                    lPhone=student.getPhoneStudentValue();
                    checkResult=checkUser(lFirstName, lLastName,lEmail,lPassword,lConfirmPassword,lPhone,lAddress,true,0);
                    if (checkResult){
                        student1=new Student(lEmail,lFirstName,lLastName,lPassword,lPhone,lAddress);
                        dbHelperStudent.insertStudent(student1);
                        Toast.makeText(SignUPMainActivity.this, "Singed Up Successfully", Toast.LENGTH_SHORT).show();
                    }
                } else if (!signUpInstructor.isEnabled()) {
                    lAddress=instructor.getAddressInstructorValue();
                    lPhone=instructor.getPhoneInstructorValue();
                    lSpecialization=instructor.getSpecializationValue();
                    lListOfCourses=instructor.getListOfCoursesValue();
                    bsc=instructor.isCheckedBSc();
                    msc=instructor.isCheckedMSc();
                    phd=instructor.isCheckedPhD();
                    checkResult=checkUser(lFirstName, lLastName,lEmail,lPassword,lConfirmPassword,lPhone,lAddress,true,1);
                    String checkDegree=checkDegree(bsc,msc,phd);
                    List<String> coursesList =convertStringToList(lListOfCourses);
                    if(checkResult&&!coursesList.isEmpty()){
                        instructor1=new Instructor(lEmail,lFirstName,lLastName,lPassword,lPhone,lAddress,lSpecialization,checkDegree,coursesList);
                        dbHelperInstructor.insertInstructor(instructor1);
                        Toast.makeText(SignUPMainActivity.this, "Singed Up Successfully", Toast.LENGTH_SHORT).show();
                    }
                } else if (!signUpAdmin.isEnabled()) {
                    boolean isChecked=checkUser(lFirstName, lLastName,lEmail,lPassword,lConfirmPassword,null,null,false,2);
                    if (isChecked){
                        admin=new Admin(lEmail,lFirstName,lLastName,lPassword);
                        dbHelperAdmin.insertAdmin(admin);
                        Toast.makeText(SignUPMainActivity.this, "Singed Up Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public boolean checkUser(String firstName,String lastName,String email,String password,String confirmPassword,String phone,String address,boolean selection,int choice){
        if (selection){
            if (choice==0){
                if (email.isEmpty() || email.isBlank() || dbHelperStudent.isRegistered(email)) {
                    Toast.makeText(SignUPMainActivity.this, "This Email not Valid!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else if (choice==1){
                if (email.isEmpty() || email.isBlank() || dbHelperInstructor.isRegistered(email)) {
                    Toast.makeText(SignUPMainActivity.this, "This Email not Valid!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            if (firstName.isEmpty() || firstName.isBlank()) {
                Toast.makeText(SignUPMainActivity.this, "This First Name not Valid!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (lastName.isEmpty() || lastName.isBlank()) {
                Toast.makeText(SignUPMainActivity.this, "This last Name not Valid!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (password.isEmpty() || password.isBlank()) {
                Toast.makeText(SignUPMainActivity.this, "This password not Valid!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!confirmPassword.equals(password)) {
                Toast.makeText(SignUPMainActivity.this, "Two password not match each other!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (phone.isEmpty() || phone.isBlank()) {
                Toast.makeText(SignUPMainActivity.this, "This phone Number not Valid!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (address.isEmpty() || address.isBlank()) {
                Toast.makeText(SignUPMainActivity.this, "This Address not Valid!", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }else {
            if (email.isEmpty() || email.isBlank() || dbHelperAdmin.isRegistered(email)) {
                Toast.makeText(SignUPMainActivity.this, "This Email not Valid!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (firstName.isEmpty() || firstName.isBlank()) {
                Toast.makeText(SignUPMainActivity.this, "This First Name not Valid!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (lastName.isEmpty() || lastName.isBlank()) {
                Toast.makeText(SignUPMainActivity.this, "This last Name not Valid!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (password.isEmpty() || password.isBlank()) {
                Toast.makeText(SignUPMainActivity.this, "This password not Valid!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!confirmPassword.equals(password)) {
                Toast.makeText(SignUPMainActivity.this, "Two password not match each other!", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

    }
    public String checkDegree(boolean bsc, boolean msc, boolean phd) {
        StringBuilder result = new StringBuilder();
        if (bsc && msc && phd) {
            result.append("bsc And msc And phd.");
        } else {
            if (bsc == msc) {
                result.append("bsc And msc.");
            }
            if (bsc == phd) {
                result.append("bsc and phd.");
            }
            if (msc == phd) {
                result.append("msc and phd.");
            }
        }
        return result.toString();
    }
    public List<String> convertStringToList(String input) {
        ArrayList<String>listOfCourses=new ArrayList<>();
        if (input.isEmpty() || input.isBlank()) {
            Toast.makeText(SignUPMainActivity.this, "This Courses not Valid!", Toast.LENGTH_SHORT).show();
            return null;
        }
        else {
            String[] splitArray = input.split(","); // Split the string by space
            for (String s:splitArray) {
                if (dbHelperCourse.isCourseExists(Integer.parseInt(s))){
                    listOfCourses.add(s);
                } else {
                    Toast.makeText(SignUPMainActivity.this, "This Course : "+s+" Not Found", Toast.LENGTH_SHORT).show();
                }
            }
            return listOfCourses; // Convert array to list
        }
    }
}