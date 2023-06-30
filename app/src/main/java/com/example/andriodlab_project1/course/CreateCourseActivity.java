package com.example.andriodlab_project1.course;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CreateCourseActivity extends DrawerBaseActivity {
    private CourseDataBaseHelper dbHelper;
    private static int RESULT_LOAD_IMAGE = 1;
    private EditText CourseTitleInput;
    private EditText CourseMainTopicsInput;
    private Map.Entry<String, String> entry;
    private String value;

    byte[] blob;
    ActivityCreateCourseBinding activityCreateCourseBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreateCourseBinding = ActivityCreateCourseBinding.inflate(getLayoutInflater());
        setContentView(activityCreateCourseBinding.getRoot());
        //setContentView(R.layout.activity_create_course);
        CourseTitleInput=findViewById(R.id.CourseTitleInput);
        CourseMainTopicsInput=findViewById(R.id.CourseMainTopicsInput);
        TextView Prerequisites = findViewById(R.id.list);
        dbHelper = new CourseDataBaseHelper(this);

        List<Map.Entry<String, String>> continents = dbHelper.getAllCourses();

        boolean[] selectedContinents = new boolean[continents.size()];

        ArrayList<String> continentsList = new ArrayList<>();
        Prerequisites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateCourseActivity.this);
                builder.setTitle("Prerequisites:");
                builder.setCancelable(false);
                CharSequence[] items = convertListToCharSequenceArray(continents);

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


        //////////////////

        Button buttonLoadImage = (Button) findViewById(R.id.InsertPhotoButton);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        Button SubmitDataButton = findViewById(R.id.Update);
        SubmitDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName=CourseTitleInput.getText().toString();
                ArrayList<String> courseTopics=convertStringToList(CourseMainTopicsInput.getText().toString());
                Course course=new Course(courseName,courseTopics,continentsList,null);
                if (CourseTitleInput.getText().toString().isEmpty()||CourseTitleInput.getText().toString().isBlank()){
                    Toast.makeText(CreateCourseActivity.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
                }
                else if(CourseMainTopicsInput.getText().toString().isEmpty()||CourseMainTopicsInput.getText().toString().isBlank()){
                    Toast.makeText(CreateCourseActivity.this, "This Course Title not Valid!", Toast.LENGTH_SHORT).show();
                }else if (dbHelper.insertCourse(course)){
                    Toast.makeText(CreateCourseActivity.this, "This Courses Added successfully.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CreateCourseActivity.this, "This Courses Added Failed.", Toast.LENGTH_SHORT).show();

                }
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