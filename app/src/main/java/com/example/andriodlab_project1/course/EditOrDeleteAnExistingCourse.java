package com.example.andriodlab_project1.course;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.admin.AdminMainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EditOrDeleteAnExistingCourse extends AppCompatActivity {
    private TextView listOfCourse;
    private TextView courseNumber;
    private TextView courseTitle;
    private TextView courseMainTobic;
    private TextView preRequest;
    private CourseDataBaseHelper dbHelper;
    private Course course;
    private String[] continents;
    private Button delete;
    private Button edit;
    private int selected;

    private boolean[] selectedContinents;
    private ArrayList<Integer> continentsList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_delete_an_existing_course);
        listOfCourse = findViewById(R.id.list);
        delete = findViewById(R.id.Delete);
        edit = findViewById(R.id.Edit);
        courseNumber = findViewById(R.id.courseID);
        courseTitle = findViewById(R.id.courseTitle);
        courseMainTobic = findViewById(R.id.courseMainTobic);
        preRequest = findViewById(R.id.preRequest);
        dbHelper = new CourseDataBaseHelper(this);
        continents = dbHelper.getAllCourses().toArray(new String[0]);
        selectedContinents = new boolean[continents.length];
        continentsList = new ArrayList<>();

        listOfCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditOrDeleteAnExistingCourse.this);
                builder.setTitle("Courses :");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(continents,selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = which;  // Update the selected continent index
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        course= dbHelper.getCourseByID(Integer.parseInt(continents[selected]));
                        courseNumber.setText(String.valueOf(course.getCourseID()));
                        courseTitle.setText(course.getCourseTitle());
                        courseMainTobic.setText(convertArrayListToString(course.getCourseMainTopics()));
                        preRequest.setText(convertIntegerArrayListToString(course.getPrerequisites()));
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
                    }
                });
                builder.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.deleteCourse(Integer.parseInt(continents[selected]))){
                    Toast.makeText(EditOrDeleteAnExistingCourse.this, "This Courses Deleted Successfully.", Toast.LENGTH_SHORT).show();
                    continents= removeElementAtIndex(continents,selected);
                }else {
                    Toast.makeText(EditOrDeleteAnExistingCourse.this, "This Courses Deleted Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EditOrDeleteAnExistingCourse.this, EditPage.class);
                startActivity(intent);
            }
        });
    }

    public static String convertArrayListToString(ArrayList<String> arrayList) {
        return String.join(",", arrayList);
    }
    public static String convertIntegerArrayListToString(ArrayList<Integer> arrayList) {
        return arrayList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
    public static String[] removeElementAtIndex(String[] array, int index) {
        String[] newArray = new String[array.length - 1];
        int newArrayIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (i != index) {
                newArray[newArrayIndex] = array[i];
                newArrayIndex++;
            }
        }
        return newArray;
    }
}