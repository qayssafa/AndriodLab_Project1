package com.example.andriodlab_project1.student;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.common.User;
import com.example.andriodlab_project1.databinding.ActivityStudentMainBinding;

public class StudentMainActivity extends StudentDrawerBaseActivity {
    private Button enroll;
    private Button messages;

    private TextView StudntName;

    StudentDataBaseHelper studentDataBaseHelper;
    public static User user = new User();

    ActivityStudentMainBinding activityStudentMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStudentMainBinding = ActivityStudentMainBinding.inflate(getLayoutInflater());
        setContentView(activityStudentMainBinding.getRoot());
        //setContentView(R.layout.activity_student_main);
        enroll=findViewById(R.id.enrollButtonAndSee);
        messages=findViewById(R.id.messages);
        StudntName = (TextView)findViewById(R.id.StudentName);
        studentDataBaseHelper = new StudentDataBaseHelper(this);
        StudntName.setText(studentDataBaseHelper.getStudentByEmail(MainActivity.studentEmail).getFirstName()+" "+studentDataBaseHelper.getStudentByEmail(MainActivity.studentEmail).getLastName());

        enroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StudentMainActivity.this, SearchAndViewCourseAreAvailable.class);
                startActivity(intent);
            }
        });
        messages.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StudentMainActivity.this, messages.class);
                startActivity(intent);
            }
        });
    }

}
