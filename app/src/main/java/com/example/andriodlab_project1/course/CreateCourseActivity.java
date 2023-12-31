package com.example.andriodlab_project1.course;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.databinding.ActivityCreateCourseBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CreateCourseActivity extends DrawerBaseActivity {
    private CourseDataBaseHelper dbHelper;
    private EditText CourseTitleInput;
    private EditText CourseMainTopicsInput;
    private Map.Entry<String, String> entry;
    private String value;

    public static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStore;

    byte[] blob;
    ActivityCreateCourseBinding activityCreateCourseBinding;
    private List<Map.Entry<String, String>> continents;
    private boolean[] selectedContinents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreateCourseBinding = ActivityCreateCourseBinding.inflate(getLayoutInflater());
        setContentView(activityCreateCourseBinding.getRoot());
        //setContentView(R.layout.activity_create_course);
        CourseTitleInput = findViewById(R.id.CourseTitleInput);
        CourseMainTopicsInput = findViewById(R.id.CourseMainTopicsInput);
        TextView Prerequisites = findViewById(R.id.list);
        dbHelper = new CourseDataBaseHelper(this);

        continents = dbHelper.getAllCourses();

        selectedContinents = new boolean[continents.size()];

        ArrayList<String> continentsList = new ArrayList<>();
        Prerequisites.setOnClickListener(v -> {
            continents = dbHelper.getAllCourses();
            selectedContinents = new boolean[continents.size()];
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateCourseActivity.this);
            builder.setTitle("Prerequisites:");
            builder.setCancelable(false);
            CharSequence[] items = convertListToCharSequenceArray(continents);

            builder.setMultiChoiceItems(items, selectedContinents, (dialog, which, isChecked) -> {
                if (isChecked) {
                    entry = continents.get(which);
                    value = entry.getValue();
                    continentsList.add(value);
                    Collections.sort(continentsList);
                } else {
                    if (which >= 0 && which < continentsList.size()) {
                        continentsList.remove(which);
                    } else {
                        // Handle boundary condition: index out of range
                        // Display an error message or handle it as per your requirement
                    }
                }
            });
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.setNeutralButton("Clear All", (dialog, which) -> {
                Arrays.fill(selectedContinents, false);
                continentsList.clear();
            });
            builder.show();
        });


        //////////////////

        Button loadImage = findViewById(R.id.InsertPhotoButton);
        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        Button SubmitDataButton = findViewById(R.id.Update);
        SubmitDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = CourseTitleInput.getText().toString();
                ArrayList<String> courseTopics = convertStringToList(CourseMainTopicsInput.getText().toString());
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaultphoto);
                Course course = new Course(courseName, courseTopics, continentsList, imageToStore == null? bitmap : imageToStore);
                if (CourseTitleInput.getText().toString().isEmpty() || CourseTitleInput.getText().toString().isBlank()) {
                    Toast.makeText(CreateCourseActivity.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
                } else if (CourseMainTopicsInput.getText().toString().isEmpty() || CourseMainTopicsInput.getText().toString().isBlank()) {
                    Toast.makeText(CreateCourseActivity.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
                } else if (dbHelper.insertCourse(course)) {
                    Toast.makeText(CreateCourseActivity.this, "This Courses Added successfully.", Toast.LENGTH_SHORT).show();
                    CourseTitleInput.setText("");
                    CourseMainTopicsInput.setText("");

                } else {
                    Toast.makeText(CreateCourseActivity.this, "This Courses Added Failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void chooseImage() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static CharSequence[] convertListToCharSequenceArray(List<Map.Entry<String, String>> list) {
        CharSequence[] array = new CharSequence[list.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : list) {
            array[i++] = entry.getValue();
        }
        return array;
    }

    public ArrayList<String> convertStringToList(String input) {
        if (input.isEmpty() || input.isBlank()) {
            Toast.makeText(CreateCourseActivity.this, "This Courses Topics not Valid!", Toast.LENGTH_SHORT).show();
            return null;
        } else {
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