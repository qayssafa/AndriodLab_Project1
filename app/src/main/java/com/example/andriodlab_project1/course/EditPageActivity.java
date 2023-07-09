package com.example.andriodlab_project1.course;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.admin.AdminMainActivity;
import com.example.andriodlab_project1.enrollment.EnrollmentDataBaseHelper;
import com.example.andriodlab_project1.notification.NotificationDataBaseHelper;

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
    private NotificationDataBaseHelper notificationDataBaseHelper;
    private EnrollmentDataBaseHelper enrollmentDataBaseHelper;
    private TextView Prerequisites;
    private TextView id;
    private Button SubmitDataButton;

    private Button insertPhoto;
    private ImageButton GoBackButton;
    private boolean[] selectedContinents;
    private ArrayList<String> continentsList;
    private Map.Entry<String, String> entry;
    private String value;

    public static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStoreNew;



    private int idF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        dbHelper = new CourseDataBaseHelper(this);
        enrollmentDataBaseHelper = new EnrollmentDataBaseHelper(this);
        notificationDataBaseHelper = new NotificationDataBaseHelper(this);
        CourseTitleInput = findViewById(R.id.EditCourseTitleInput);
        CourseMainTopicsInput = findViewById(R.id.EditCourseMainTopicsInput);
        Prerequisites = findViewById(R.id.Editlist);
        id = findViewById(R.id.courseIdNew);
        GoBackButton = findViewById(R.id.BackButton);
        insertPhoto = findViewById(R.id.InsertPhotoButton);

        continents = dbHelper.getAllCourses();
        SubmitDataButton = findViewById(R.id.EditUpdate);
        continentsList = new ArrayList<>();
        CharSequence[] items = convertListToCharSequenceArray(continents);
        selectedContinents = new boolean[continents.size()];

        GoBackButton.setOnClickListener(v -> startActivity(new Intent(EditPageActivity.this, EditOrDeleteAnExistingCourseActivity.class)));
        Prerequisites.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditPageActivity.this);
            builder.setTitle("Edit Prerequisites:");
            builder.setCancelable(false);
            builder.setMultiChoiceItems(items, selectedContinents, (dialog, which, isChecked) -> {
                if (isChecked) {
                    entry = continents.get(which);
                    value = entry.getValue();
                    continentsList.add(value);
                    id.setText(entry.getKey());
                    Collections.sort(continentsList);
                } else {
                    continentsList.remove(which);
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

        insertPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        SubmitDataButton.setOnClickListener(v -> {

            while (CourseTitleInput.getText().toString().isEmpty() || CourseTitleInput.getText().toString().isBlank()) {
                Toast.makeText(EditPageActivity.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
            }
            String courseName = CourseTitleInput.getText().toString();

            while (CourseMainTopicsInput.getText().toString().isEmpty() || CourseMainTopicsInput.getText().toString().isBlank()) {
                Toast.makeText(EditPageActivity.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
            }
            Bitmap Photo1 = dbHelper.getImage(courseName);
            ArrayList<String> courseTopics = convertStringToList(CourseMainTopicsInput.getText().toString());
            Bitmap toSave;
            if(imageToStoreNew != null)
                toSave = imageToStoreNew;
            else
                toSave = Photo1;
            Course course = new Course(courseName, courseTopics, continentsList, toSave);
            course.setCourseID(EditOrDeleteAnExistingCourseActivity.id);
            if (dbHelper.updateCourse(course)) {
                ArrayList<String> studentsAreTakenCourse = enrollmentDataBaseHelper.getStudentsByCourseId(EditOrDeleteAnExistingCourseActivity.id);
                if (!studentsAreTakenCourse.isEmpty()) {
                    for (String s : studentsAreTakenCourse) {
                        notificationDataBaseHelper.insertNotification(s, "This Course " + dbHelper.getCourseName(EditOrDeleteAnExistingCourseActivity.id) + " its updated check Please.");
                    }
                }
                Toast.makeText(EditPageActivity.this, "This Courses Updated successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditPageActivity.this, "This Courses Updated Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private ArrayList<String> convertStringToList(String input) {
        if (input.isEmpty() || input.isBlank()) {
            Toast.makeText(EditPageActivity.this, "This Courses Topics not Valid!", Toast.LENGTH_SHORT).show();
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

    public static CharSequence[] convertListToCharSequenceArray(List<Map.Entry<String, String>> list) {
        if (list.isEmpty()) {
            return new CharSequence[0]; // Return an empty array if the list is empty
        }

        int nonMatchingEntriesCount = 0;
        for (Map.Entry<String, String> entry : list) {
            if (entry.getKey().equals(EditOrDeleteAnExistingCourseActivity.id + "")) {
                nonMatchingEntriesCount++;
            }
        }

        CharSequence[] array = new CharSequence[list.size() - nonMatchingEntriesCount];
        int i = 0;
        for (Map.Entry<String, String> entry : list) {
            if (!entry.getKey().equals(EditOrDeleteAnExistingCourseActivity.id + "")) {
                array[i++] = entry.getValue();
            }
        }

        return array;
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
                imageToStoreNew = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}