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
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.databinding.ActivityViewProfilesOfStudentAndInstructorBinding;
import com.example.andriodlab_project1.instructor.Instructor;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.student.Student;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

public class ViewProfilesOfStudentAndInstructor extends DrawerBaseActivity implements ViewStudentProfileFragment.communicatorStudent {
    EditText UserEmail;

    private StudentDataBaseHelper studentDataBaseHelper;
    private InstructorDataBaseHelper instructorDataBaseHelper;


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
        UserEmail = (EditText) findViewById(R.id.UserEmailInput);
        //final ViewInstructorProfileFragment instructor = new ViewInstructorProfileFragment();
        final ViewStudentProfileFragment student = new ViewStudentProfileFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();



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
                  /* FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(instructor);
                    fragmentTransaction.add(R.id.flayout, student, "student");
                    fragmentTransaction.commit();*/
                   /* Student student1=studentDataBaseHelper.getStudentByEmail(UserEmail.getText().toString());*/
                   // student.changeDataForStudent(student1.getEmail(),student1.getFirstName(),student1.getLastName(),student1.getMobileNumber(),student1.getAddress());
                }else if(!(UserEmail.getText().toString().isEmpty())&&instructorDataBaseHelper.isRegistered(UserEmail.getText().toString())){
                   /* FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(student);
                    fragmentTransaction.add(R.id.flayout, instructor, "instructor");
                    fragmentTransaction.commit();
                    //instructor.changeDataForInstructor();
                    Instructor instructor1=instructorDataBaseHelper.getInstructorByEmail(UserEmail.getText().toString());
                    if(instructor.UserDegreeText != null){
                        instructor.UserDegreeText.setText(String.valueOf(instructor1.getLastName()));
                    } else {
                        Toast.makeText(ViewProfilesOfStudentAndInstructor.this, "This ZEFT PROJECT", Toast.LENGTH_SHORT).show();
                    }*/
                }else {
                    Toast.makeText(ViewProfilesOfStudentAndInstructor.this, "This Email its not Valid.", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    @Override
    public void respond(Student lStudent) {

    }
}