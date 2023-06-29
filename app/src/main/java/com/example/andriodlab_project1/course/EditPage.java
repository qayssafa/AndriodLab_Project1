package com.example.andriodlab_project1.course;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EditPage extends AppCompatActivity {
    private EditText CourseTitleInput;
    private EditText CourseMainTopicsInput;
    private String[] continents;
    private CourseDataBaseHelper dbHelper;
    private TextView Prerequisites;
    private Button SubmitDataButton;
    private boolean[] selectedContinents;
    private ArrayList<Integer> continentsList;
    private int idF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        dbHelper = new CourseDataBaseHelper(this);
        CourseTitleInput=findViewById(R.id.EditCourseTitleInput);
        CourseMainTopicsInput=findViewById(R.id.EditCourseMainTopicsInput);
        Prerequisites = findViewById(R.id.Editlist);
        continents = dbHelper.getAllCourses().toArray(new String[0]);
        selectedContinents = new boolean[continents.length];
        SubmitDataButton = findViewById(R.id.EditUpdate);
        continentsList = new ArrayList<>();
        Prerequisites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPage.this);
                builder.setTitle("Edit Prerequisites:");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(continents, selectedContinents, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            continentsList.add(which);
                            Collections.sort(continentsList);
                        }else{
                            continentsList.remove(Integer.valueOf(which));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Arrays.fill(selectedContinents, false);
                        continentsList.clear();
                    }
                });
                builder.show();
            }
        });
        SubmitDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                while (CourseTitleInput.getText().toString().isEmpty()||CourseTitleInput.getText().toString().isBlank()){
                    Toast.makeText(EditPage.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
                }
                String courseName=CourseTitleInput.getText().toString();

                while (CourseMainTopicsInput.getText().toString().isEmpty()||CourseMainTopicsInput.getText().toString().isBlank()){
                    Toast.makeText(EditPage.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
                }
                ArrayList<String> courseTopics=convertStringToList(CourseMainTopicsInput.getText().toString());
                Course course=new Course(courseName,courseTopics,continentsList,null);
                EditOrDeleteAnExistingCourse editOrDeleteAnExistingCourse;
                course.setCourseID(EditOrDeleteAnExistingCourse.id);
                if (dbHelper.updateCourse(course)){
                    Toast.makeText(EditPage.this, "This Courses Updated successfully.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditPage.this, "This Courses Updated Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private ArrayList<String> convertStringToList(String input) {
        if (input.isEmpty() || input.isBlank()) {
            Toast.makeText(EditPage.this, "This Courses Topics not Valid!", Toast.LENGTH_SHORT).show();
            return null;
        }
        else {
            String trimmedInput = input.trim();
            if (trimmedInput.endsWith(",")) {
                trimmedInput = trimmedInput.substring(0, trimmedInput.length() - 1);
            }

            String[] splitArray = trimmedInput.split(",");
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(splitArray));
            return arrayList;
        }
    }
}