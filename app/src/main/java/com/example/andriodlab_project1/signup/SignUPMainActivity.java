package com.example.andriodlab_project1.signup;

import static com.example.andriodlab_project1.course.CreateCourseActivity.PICK_IMAGE_REQUEST;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.MainActivity;
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
    public static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStore;

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
        TextView textView = findViewById(R.id.login);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUPMainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button loadImage = findViewById(R.id.Insetphoto);
        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        signUpInstructor.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(student);
            fragmentTransaction.add(R.id.mm, instructor, "instructor");
            fragmentTransaction.commit();
            signUpInstructor.setEnabled(false);
            signUpStudent.setEnabled(true);
            signUpAdmin.setEnabled(true);
        });
        signUpAdmin.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(instructor);
            fragmentTransaction.remove(student);
            fragmentTransaction.commit();
            signUpInstructor.setEnabled(true);
            signUpStudent.setEnabled(true);
            signUpAdmin.setEnabled(false);
        });
        signUpStudent.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(instructor);
            fragmentTransaction.add(R.id.mm, student, "student");
            fragmentTransaction.commit();
            signUpInstructor.setEnabled(true);
            signUpStudent.setEnabled(false);
            signUpAdmin.setEnabled(true);
        });

        signUp.setOnClickListener(view -> {
            String lFirstName = firstName.getText().toString();
            String lLastName = lastName.getText().toString();
            String lEmail = email.getText().toString();
            String lConfirmPassword = confirmPassword.getText().toString();
            String lPassword = password.getText().toString();
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
                lAddress = student.getAddressStudentValue();
                lPhone = student.getPhoneStudentValue();
                checkResult = checkUser(lFirstName, lLastName, lEmail, lPassword, lConfirmPassword, lPhone, lAddress, true, 0);
                if (checkResult) {
                    //Bitmap studentPhoto = imageToStore;
                    student1 = new Student(lEmail, lFirstName, lLastName, lPassword, lPhone, lAddress,imageToStore);
                    dbHelperStudent.insertStudent(student1);
                    Toast.makeText(SignUPMainActivity.this, "Singed Up Successfully", Toast.LENGTH_SHORT).show();
                    firstName.setText("");
                    lastName.setText("");
                    email.setText("");
                    password.setText("");
                    confirmPassword.setText("");
                    student.setEmpty();

                }
            } else if (!signUpInstructor.isEnabled()) {
                lAddress = instructor.getAddressInstructorValue();
                lPhone = instructor.getPhoneInstructorValue();
                lSpecialization = instructor.getSpecializationValue();
                lListOfCourses = instructor.getListOfCoursesValue();
                bsc = instructor.isCheckedBSc();
                msc = instructor.isCheckedMSc();
                phd = instructor.isCheckedPhD();

                if(bsc == true){
                    instructor.checkBoxMSc.setChecked(false);
                    instructor.checkBoxPhD.setChecked(false);
                }else if(msc == true){
                    instructor.checkBoxBSc.setChecked(false);
                    instructor.checkBoxPhD.setChecked(false);
                }else if(phd == true){
                    instructor.checkBoxBSc.setChecked(false);
                    instructor.checkBoxMSc.setChecked(false);
                }
                checkResult = checkUser(lFirstName, lLastName, lEmail, lPassword, lConfirmPassword, lPhone, lAddress, true, 1);
                String checkDegree = checkDegree(bsc, msc, phd);
                List<String> coursesList = convertStringToList(lListOfCourses);
                if (checkResult && !coursesList.isEmpty()) {
                    instructor1 = new Instructor(lEmail, lFirstName, lLastName, lPassword, lPhone, lAddress, lSpecialization, checkDegree, coursesList);
                    dbHelperInstructor.insertInstructor(instructor1);
                    Toast.makeText(SignUPMainActivity.this, "Singed Up Successfully", Toast.LENGTH_SHORT).show();
                    firstName.setText("");
                    lastName.setText("");
                    email.setText("");
                    password.setText("");
                    confirmPassword.setText("");
                    instructor.setEmpty();
                }
            } else if (!signUpAdmin.isEnabled()) {
                boolean isChecked = checkUser(lFirstName, lLastName, lEmail, lPassword, lConfirmPassword, null, null, false, 2);
                if (isChecked) {
                    admin = new Admin(lEmail, lFirstName, lLastName, lPassword);
                    dbHelperAdmin.insertAdmin(admin);
                    Toast.makeText(SignUPMainActivity.this, "Singed Up Successfully", Toast.LENGTH_SHORT).show();
                    firstName.setText("");
                    lastName.setText("");
                    email.setText("");
                    password.setText("");
                    confirmPassword.setText("");
                }
            }
        });
    }

    public void chooseImage() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    public String checkDegree(boolean hasBsc, boolean hasMsc, boolean hasPhd) {
        if (hasPhd) {
            return "Has Ph.D.";
        }
        if (hasMsc) {
            return "Has M.Sc.";
        }
        if (hasBsc) {
            return "Has B.Sc.";
        }
        return "No degree found.";
    }

    public List<String> convertStringToList(String input) {
        ArrayList<String> listOfCourses = new ArrayList<>();
        if (input.isEmpty() || input.isBlank()) {
            Toast.makeText(SignUPMainActivity.this, "This Courses not Valid!", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            String[] splitArray = input.split(","); // Split the string by space
            for (String s : splitArray) {
                if (s.matches("^-?\\d+$")) {
                    if (dbHelperCourse.isCourseExists(Integer.parseInt(s))) {
                        listOfCourses.add(dbHelperCourse.getCourseName(Integer.parseInt(s)));
                    } else {
                        Toast.makeText(SignUPMainActivity.this, "This Course : " + s + " Not Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUPMainActivity.this, "This Course : " + s + " Not Valid", Toast.LENGTH_SHORT).show();
                }
            }
            return listOfCourses; // Convert array to list
        }
    }
}