package com.example.andriodlab_project1.admin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.databinding.ActivityViewProfilesOfStudentAndInstructorBinding;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

public class ViewProfilesOfStudentAndInstructor extends DrawerBaseActivity {
    EditText UserEmail;
    EditText UserFname;
    EditText UserLname;
    private StudentDataBaseHelper studentDataBaseHelper;
    private InstructorDataBaseHelper instructorDataBaseHelper;

    TextView UserFnameText;
    TextView UserLnameText;
    TextView UserPhotoText;

    ImageView Userphoto;

    Button instructorButton;
    Button studentButton;

    ActivityViewProfilesOfStudentAndInstructorBinding activityViewProfilesOfStudentAndInstructorBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewProfilesOfStudentAndInstructorBinding = ActivityViewProfilesOfStudentAndInstructorBinding.inflate(getLayoutInflater());
        setContentView(activityViewProfilesOfStudentAndInstructorBinding.getRoot());
        //setContentView(R.layout.activity_view_profiles_of_student_and_instructor);
        studentDataBaseHelper=new StudentDataBaseHelper(this);
        instructorDataBaseHelper=new InstructorDataBaseHelper(this);
        LinearLayout linearLayout = findViewById(R.id.root_layoutt);
//        linearLayout.setVisibility(View.INVISIBLE);


        UserEmail = (EditText) findViewById(R.id.UserEmailInput);

        final ViewInstructorProfileFragment instructor = new ViewInstructorProfileFragment();
        final ViewStudentProfileFragment student = new ViewStudentProfileFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();

        instructorButton = findViewById(R.id.ins);
        studentButton = findViewById(R.id.stu);

       /* UserFname = (EditText) findViewById(R.id.UserFname);
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
        UserPhotoText.setPadding(40, UserPhotoText.getPaddingTop(), UserPhotoText.getPaddingRight(), UserPhotoText.getPaddingBottom());*/

        instructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(student);
                fragmentTransaction.add(R.id.flayout, instructor, "instructor");
                fragmentTransaction.commit();
            }
        });

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(instructor);
                fragmentTransaction.add(R.id.flayout, student, "student");
                fragmentTransaction.commit();
            }
        });

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

            //for student
                if(!(UserEmail.getText().toString().isEmpty())&&studentDataBaseHelper.isRegistered(UserEmail.getText().toString())){

                    linearLayout.setVisibility(View.VISIBLE);

                }
                //for instructor
                if(!(UserEmail.getText().toString().isEmpty())&&instructorDataBaseHelper.isRegistered(UserEmail.getText().toString())){

                    linearLayout.setVisibility(View.VISIBLE);

                }
            }
        });

    }
}