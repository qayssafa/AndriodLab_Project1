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

        UserEmail = (EditText)findViewById(R.id.UserEmail);
        UserFirstName = (EditText)findViewById(R.id.UserFirstName);
        UserLastName = (EditText)findViewById(R.id.userLastName);
        UserPassword = (EditText)findViewById(R.id.password);
        UserConfirmPassword = (EditText)findViewById(R.id.ConfirmPassword);

        photoUpload = (Button)findViewById(R.id.UploadButton);
    }
}