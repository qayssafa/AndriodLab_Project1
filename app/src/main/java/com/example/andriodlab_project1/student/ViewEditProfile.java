package com.example.andriodlab_project1.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.andriodlab_project1.R;

public class ViewEditProfile extends AppCompatActivity {
    EditText UserEmail;
    EditText UserFirstName;
    EditText UserLastName;
    EditText UserPassword;
    EditText UserConfirmPassword;
    Button photoUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_profile);

        UserEmail = findViewById(R.id.UserEmail);
        UserFirstName = findViewById(R.id.UserFirstName);
        UserLastName = findViewById(R.id.userLastName);
        UserPassword = findViewById(R.id.password);
        UserConfirmPassword = findViewById(R.id.ConfirmPassword);

        photoUpload = findViewById(R.id.UploadButton);
    }
}