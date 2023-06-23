package com.example.andriodlab_project1.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.common.DataBaseHelper;
import com.example.andriodlab_project1.common.MultiSelectSpinner;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class CreateCourseActivity extends AppCompatActivity {
    private CourseDataBaseHelper dbHelper;
    private MultiSelectSpinner multiSelectSpinner;
    private static int RESULT_LOAD_IMAGE = 1;
    private EditText courseId;
    private EditText CourseTitleInput;
    private EditText CourseMainTopicsInput;
    byte[] blob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        courseId=findViewById(R.id.courseId);
        CourseTitleInput=findViewById(R.id.CourseTitleInput);
        CourseMainTopicsInput=findViewById(R.id.CourseMainTopicsInput);
        dbHelper = new CourseDataBaseHelper(this);
        List<String> courses = dbHelper.getAllCourses();
        String[] coursesArray = courses.toArray(new String[0]);

        multiSelectSpinner = new MultiSelectSpinner(this, coursesArray);
        Button btnShowSpinner = findViewById(R.id.PrerequisitesInput);
        btnShowSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectSpinner.showDialog();
            }
        });

        Button buttonLoadImage = (Button) findViewById(R.id.InsertPhotoButton);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        Button SubmitDataButton = findViewById(R.id.submitData);
        SubmitDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedCourses = multiSelectSpinner.getSelectedItems();
                while (courseId.getText().toString().isEmpty()||courseId.getText().toString().isBlank()||dbHelper.isCourseExists(courseId.getText().toString())){
                    Toast.makeText(CreateCourseActivity.this, "This Course Number not Valid!", Toast.LENGTH_SHORT).show();
                }
                String courseNumber=courseId.getText().toString();
                while (CourseTitleInput.getText().toString().isEmpty()||CourseTitleInput.getText().toString().isBlank()){
                    Toast.makeText(CreateCourseActivity.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
                }
                String courseName=CourseTitleInput.getText().toString();

                while (CourseMainTopicsInput.getText().toString().isEmpty()||CourseMainTopicsInput.getText().toString().isBlank()){
                    Toast.makeText(CreateCourseActivity.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
                }
                String courseTopics=CourseMainTopicsInput.getText().toString();
                //courseTopics should be list
                //check errors
                Course course=new Course(courseNumber,courseName,courseTopics,multiSelectSpinner.getSelectedItems(),blob);
                dbHelper.insertCourse(course);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            // Convert to Blob
            try {
                File file = new File(picturePath);
                blob = new byte[(int) file.length()];

                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(blob);
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}