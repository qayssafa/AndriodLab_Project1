package com.example.andriodlab_project1.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.andriodlab_project1.R;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.andriodlab_project1.InstructorDrawerBaswActivity;
import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.databinding.ActivityInstructorProfileBinding;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.databinding.ActivityStudentProfileBinding;
import com.example.andriodlab_project1.instructor.Instructor;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.instructor.InstructorProfileActivity;

import java.util.regex.Pattern;

public class StudentProfileActivity extends StudentDrawerBaseActivity {

    ActivityStudentProfileBinding activityStudentProfileBinding;

    EditText firstName, lastName, mobileNumber, address, specialization, degree, password,insEmail;

    TextView StudentName;
    Button editProfile;

    private StudentDataBaseHelper studentDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStudentProfileBinding = ActivityStudentProfileBinding.inflate(getLayoutInflater());
        setContentView(activityStudentProfileBinding.getRoot());
//        setContentView(R.layout.activity_student_profile);

        firstName = findViewById(R.id.InsFirstName);
        lastName = findViewById(R.id.InsLastName);
        mobileNumber = findViewById(R.id.InsMobileNumber);
        address = findViewById(R.id.InsAddress);
        password = findViewById(R.id.inspassword);
        editProfile = findViewById(R.id.buttonEditProfile);
        StudentName = findViewById(R.id.inccName);
        insEmail = findViewById(R.id.insEmail);


        studentDataBaseHelper = new StudentDataBaseHelper(this);
        StudentName.setText(studentDataBaseHelper.getStudentByEmail(MainActivity.studentEmail).getFirstName() + " " + studentDataBaseHelper.getStudentByEmail(MainActivity.studentEmail).getLastName());
        Student student = studentDataBaseHelper.getStudentByEmail(MainActivity.studentEmail);
        setStudentData(student);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNameText = firstName.getText().toString();
                String lastNameText = lastName.getText().toString();
                String mobileNumberText = mobileNumber.getText().toString();
                String addressText = address.getText().toString();
                String passwordText = password.getText().toString();
                String insEmailText = insEmail.getText().toString();
                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                Pattern emailPattern = Pattern.compile(emailRegex);

                if (firstNameText.isEmpty() || firstNameText.isBlank() || firstNameText.length() < 3 || firstNameText.length() > 20) {
                    Toast.makeText(StudentProfileActivity.this, "First name must be between 3 and 20 characters!", Toast.LENGTH_SHORT).show();
                }
                else if (lastNameText.isEmpty() || lastNameText.isBlank() || lastNameText.length() < 3 || lastNameText.length() > 20) {
                    showToastMessage("Last name must be between 3 and 20 characters!");
                }else  if (passwordText.isEmpty() || passwordText.isBlank() || passwordText.length() < 8 || passwordText.length() > 15 ||
                        !passwordText.matches(".*\\d.*") || !passwordText.matches(".*[a-z].*") || !passwordText.matches(".*[A-Z].*")) {
                    showToastMessage("Password must be between 8 and 15 characters and contain at least one number, one lowercase letter, and one uppercase letter!");
                } else if (mobileNumberText.isEmpty() || mobileNumberText.isBlank() || mobileNumberText.length() != 10) {
                    showToastMessage("Mobile number must be 10 digits!");
                } else if (addressText.isEmpty() || addressText.isBlank() ) {
                    showToastMessage("Address field is empty ");
                } else if (insEmailText.isEmpty() || insEmailText.isBlank() || !emailPattern.matcher(insEmailText).matches()) {
                    showToastMessage("This Email not Valid!");
                }
                else {
                    Student updateStudent = new Student();
                    updateStudent.setFirstName(firstNameText);
                    updateStudent.setLastName(lastNameText);
                    updateStudent.setMobileNumber(mobileNumberText);
                    updateStudent.setAddress(addressText);
                    updateStudent.setPassword(passwordText);
                    updateStudent.setEmail(insEmailText);

                    studentDataBaseHelper.updateStudent(updateStudent);

                    Toast.makeText(StudentProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                    StudentName.setText(studentDataBaseHelper.getStudentByEmail(MainActivity.studentEmail).getFirstName() + " " + studentDataBaseHelper.getStudentByEmail(MainActivity.studentEmail).getLastName());
                }

            }
        });

    }

    private void showToastMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        TextView toastText = toastView.findViewById(android.R.id.message);
        toastText.setTextColor(Color.RED); // Set text color here
        toastView.setBackgroundColor(Color.TRANSPARENT); // Set background color here
        toast.show();
    }

    private void setStudentData(Student student) {
        insEmail.setText(student.getEmail());
        insEmail.setSelection(insEmail.getText().length());
        insEmail.clearFocus();
        firstName.setText(student.getFirstName());
        firstName.setSelection(firstName.getText().length());
        firstName.clearFocus();
        lastName.setText(student.getLastName());
        lastName.setSelection(lastName.getText().length());
        lastName.clearFocus();
        mobileNumber.setText(student.getMobileNumber());
        mobileNumber.setSelection(mobileNumber.getText().length());
        mobileNumber.clearFocus();
        address.setText(student.getAddress());
        address.setSelection(address.getText().length());
        address.clearFocus();
        password.setText(student.getPassword());
        password.setSelection(password.getText().length());
        password.clearFocus();
    }
}