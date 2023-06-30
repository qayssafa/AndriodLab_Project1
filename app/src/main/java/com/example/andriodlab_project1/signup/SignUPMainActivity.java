package com.example.andriodlab_project1.signup;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.List;
import java.util.regex.Pattern;

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
    public boolean checkUser(String firstName, String lastName, String email, String password, String confirmPassword, String phone, String address, boolean selection, int choice) {
        // Email address validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (email.isEmpty() || email.isBlank() || !emailPattern.matcher(email).matches()) {
            showToastMessage("This Email not Valid!");
            return false;
        }

        // First name validation
        if (firstName.isEmpty() || firstName.isBlank() || firstName.length() < 3 || firstName.length() > 20) {
            showToastMessage("First name must be between 3 and 20 characters!");
            return false;
        }

        // Last name validation
        if (lastName.isEmpty() || lastName.isBlank() || lastName.length() < 3 || lastName.length() > 20) {
            showToastMessage("Last name must be between 3 and 20 characters!");
            return false;
        }

        // Password validation
        if (password.isEmpty() || password.isBlank() || password.length() < 8 || password.length() > 15 ||
                !password.matches(".*\\d.*") || !password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
            showToastMessage("Password must be between 8 and 15 characters and contain at least one number, one lowercase letter, and one uppercase letter!");
            return false;
        }

        // Confirm password validation
        if (!confirmPassword.equals(password)) {
            showToastMessage("Two passwords do not match!");
            return false;
        }

        // Additional validations based on selection and choice
        if (selection) {
            if (choice == 0) {
                if (dbHelperStudent.isRegistered(email)) {
                    showToastMessage("This Email already Registered!");
                    return false;
                }
            } else if (choice == 1) {
                if (dbHelperInstructor.isRegistered(email)) {
                    showToastMessage("This Email already Registered!");
                    return false;
                }
            }

            // Phone validation
            if (phone.isEmpty() || phone.isBlank()) {
                showToastMessage("This Phone Number not Valid!");
                return false;
            }

            // Address validation
            if (address.isEmpty() || address.isBlank()) {
                showToastMessage("This Address not Valid!");
                return false;
            }
        } else {
            if (dbHelperAdmin.isRegistered(email)) {
                showToastMessage("This Email already Registered!");
                return false;
            }
        }

        return true;
    }

    private void showToastMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        TextView toastText = toastView.findViewById(android.R.id.message);
        toastText.setTextColor(Color.RED); // Set text color here
        toastView.setBackgroundColor(Color.TRANSPARENT); // Set background color here
        toast.show();
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