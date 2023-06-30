package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.common.User;
import com.example.andriodlab_project1.databinding.ActivityViewProfilesOfStudentAndInstructorBinding;

public class ViewProfilesOfStudentAndInstructor extends DrawerBaseActivity {
    EditText UserEmail;
    EditText UserFname;
    EditText UserLname;

    TextView UserFnameText;
    TextView UserLnameText;
    TextView UserPhotoText;

    ImageView Userphoto;

    ActivityViewProfilesOfStudentAndInstructorBinding activityViewProfilesOfStudentAndInstructorBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewProfilesOfStudentAndInstructorBinding = ActivityViewProfilesOfStudentAndInstructorBinding.inflate(getLayoutInflater());
        setContentView(activityViewProfilesOfStudentAndInstructorBinding.getRoot());
        //setContentView(R.layout.activity_view_profiles_of_student_and_instructor);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.INVISIBLE);


        UserEmail = (EditText) findViewById(R.id.UserEmailInput);
        UserFname = (EditText) findViewById(R.id.UserFname);
        UserLname = (EditText) findViewById(R.id.editTextText3);

        UserFnameText = (TextView) findViewById(R.id.UserFnameText);
        UserLnameText = (TextView) findViewById(R.id.UserLnameText);
        UserPhotoText = (TextView) findViewById(R.id.UserPhototext);

        Userphoto = (ImageView)findViewById(R.id.UserPhoto);

        UserFnameText.setTextSize(17);
        UserFnameText.setTextColor(Color.BLACK);
        UserFnameText.setTypeface(null, Typeface.BOLD);
        UserFnameText.setPadding(40, UserFnameText.getPaddingTop(), UserFnameText.getPaddingRight(), UserFnameText.getPaddingBottom());

        UserLnameText.setTextSize(17);
        UserLnameText.setTextColor(Color.BLACK);
        UserLnameText.setTypeface(null, Typeface.BOLD);
        UserLnameText.setPadding(40, UserLnameText.getPaddingTop(), UserLnameText.getPaddingRight(), UserLnameText.getPaddingBottom());


        UserPhotoText.setTextSize(17);
        UserPhotoText.setTextColor(Color.BLACK);
        UserPhotoText.setTypeface(null, Typeface.BOLD);
        UserPhotoText.setPadding(40, UserPhotoText.getPaddingTop(), UserPhotoText.getPaddingRight(), UserPhotoText.getPaddingBottom());

        UserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is being changed.
                String input = s.toString().trim(); // Get the input text and remove leading/trailing whitespace

                if (!input.isEmpty()) {

                } else {
                    // EditText is empty
                    // Perform desired actions
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!(UserEmail.getText().toString().isEmpty())){
                    linearLayout.setVisibility(View.VISIBLE);

                }            }
        });

    }
}