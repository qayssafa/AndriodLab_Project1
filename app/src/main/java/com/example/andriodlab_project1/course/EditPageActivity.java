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
import java.util.List;
import java.util.Map;

public class EditPageActivity extends AppCompatActivity {
    private EditText CourseTitleInput;
    private EditText CourseMainTopicsInput;
    private List<Map.Entry<String, String>> continents;
    private CourseDataBaseHelper dbHelper;
    private TextView Prerequisites;
    private Button SubmitDataButton;
    private boolean[] selectedContinents;
    private ArrayList<String> continentsList;
    private Map.Entry<String, String> entry;
    private String value;
    private int idF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        dbHelper = new CourseDataBaseHelper(this);
        CourseTitleInput=findViewById(R.id.EditCourseTitleInput);
        CourseMainTopicsInput=findViewById(R.id.EditCourseMainTopicsInput);
        Prerequisites = findViewById(R.id.Editlist);
        continents = dbHelper.getAllCourses();
        SubmitDataButton = findViewById(R.id.EditUpdate);
        continentsList = new ArrayList<>();
        CharSequence[] items = convertListToCharSequenceArray(continents);
        selectedContinents = new boolean[continents.size()];
        Prerequisites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPageActivity.this);
                builder.setTitle("Edit Prerequisites:");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(items, selectedContinents, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            entry = continents.get(which);
                            value = entry.getValue();
                            continentsList.add(value);
                            Collections.sort(continentsList);
                        }else{
                            continentsList.remove(which);
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
                    Toast.makeText(EditPageActivity.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
                }
                String courseName=CourseTitleInput.getText().toString();

                while (CourseMainTopicsInput.getText().toString().isEmpty()||CourseMainTopicsInput.getText().toString().isBlank()){
                    Toast.makeText(EditPageActivity.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
                }
                ArrayList<String> courseTopics=convertStringToList(CourseMainTopicsInput.getText().toString());
                Course course=new Course(courseName,courseTopics,continentsList,null);
                EditOrDeleteAnExistingCourseActivity editOrDeleteAnExistingCourse;
                course.setCourseID(EditOrDeleteAnExistingCourseActivity.id);
                if (dbHelper.updateCourse(course)){
                    Toast.makeText(EditPageActivity.this, "This Courses Updated successfully.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditPageActivity.this, "This Courses Updated Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private ArrayList<String> convertStringToList(String input) {
        if (input.isEmpty() || input.isBlank()) {
            Toast.makeText(EditPageActivity.this, "This Courses Topics not Valid!", Toast.LENGTH_SHORT).show();
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
    public static CharSequence[] convertListToCharSequenceArray(List<Map.Entry<String, String>> list) {
        CharSequence[] array = new CharSequence[list.size()-1];
        int i = 0;
        for (Map.Entry<String, String> entry : list) {
           if (!entry.getKey().equals(EditOrDeleteAnExistingCourseActivity.id+"")){
                array[i++] = entry.getValue();
            }
        }
        return array;
    }
}