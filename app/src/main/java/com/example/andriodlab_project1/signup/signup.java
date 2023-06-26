package com.example.andriodlab_project1.signup;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.andriodlab_project1.R;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText firstName = findViewById(R.id.editaddress);
        EditText lastName = findViewById(R.id.editspecialization);
        EditText email = findViewById(R.id.editMobileMumber);
        EditText password = findViewById(R.id.editPassword);
        EditText confirmPassword = findViewById(R.id.editConfirmPassword);
        Button SignUpInstructor = findViewById(R.id.btnSignUpInstructor);
        Button SignUpStudent = findViewById(R.id.btnSignUpStudent);
        Button SignUpAdmin = findViewById(R.id.btnSignUpAdmin);

        final instructorFragment instructor = new instructorFragment();
        final StudentFragment student = new StudentFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        SignUpAdmin.setEnabled(false);
        SignUpInstructor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(student);
                fragmentTransaction.add(R.id.root_layout, instructor, "instructor");
                fragmentTransaction.commit();
                SignUpInstructor.setEnabled(false);
                SignUpStudent.setEnabled(true);
                SignUpAdmin.setEnabled(true);
            }
        });
        SignUpAdmin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(instructor);
                fragmentTransaction.remove(student);
                fragmentTransaction.commit();
                SignUpInstructor.setEnabled(true);
                SignUpStudent.setEnabled(true);
                SignUpAdmin.setEnabled(false);


            }
        });
        SignUpStudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(instructor);
                fragmentTransaction.add(R.id.root_layout, student, "student");
                fragmentTransaction.commit();
                SignUpInstructor.setEnabled(true);
                SignUpStudent.setEnabled(false);
                SignUpAdmin.setEnabled(true);
            }
        });




    }
}