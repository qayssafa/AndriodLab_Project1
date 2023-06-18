package com.example.andriodlab_project1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andriodlab_project1.admin.AdminDataBaseHelper;
import com.example.andriodlab_project1.common.SharedPrefManager;
import com.example.andriodlab_project1.common.User;
import com.example.andriodlab_project1.common.signup;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    AdminDataBaseHelper adminDataBaseHelper = new AdminDataBaseHelper(MainActivity.this,"R1190207", null,1);
    StudentDataBaseHelper studentDataBaseHelper = new StudentDataBaseHelper(MainActivity.this,"R1190207", null,1);
    InstructorDataBaseHelper instructorDataBaseHelper = new InstructorDataBaseHelper(MainActivity.this,"R1190207", null,1);

    public static User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the data
        User newUser = new User();


        /*newUser.setEmail("q@m.com");
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setPassword("123456789Qa");
        //dataBaseHelper.insertuser(newUser);*/
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
                if (!adminDataBaseHelper.isRegistered(enteredEmail)){
                    Toast.makeText(MainActivity.this, "This email is not registered!", Toast.LENGTH_SHORT).show();
                }else if(!adminDataBaseHelper.correctSignIn(enteredEmail, enterPassword)){
                    Toast.makeText(MainActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                }else{
                    if (rememberMe.isChecked())
                        sharedPrefManager.writeString("email", email.getText().toString().trim());
                    else
                        sharedPrefManager.writeString("email", "noValue");
                    user = adminDataBaseHelper.getAdminByEmail(email.getText().toString().trim());
                    startActivity(new Intent(MainActivity.this, signup.class));
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signup.class));
            }
        });
    }


}