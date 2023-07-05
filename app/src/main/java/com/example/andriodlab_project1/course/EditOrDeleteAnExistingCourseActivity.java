package com.example.andriodlab_project1.course;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.databinding.ActivityEditOrDeleteAnExistingCourseBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EditOrDeleteAnExistingCourseActivity extends DrawerBaseActivity {
    private TextView listOfCourse;
    private TextView courseNumber;
    private TextView courseTitle;
    private TextView courseMainTobic;
    private TextView preRequest;
    private CourseDataBaseHelper dbHelper;
    private Course course;
    private List<Map.Entry<String, String>> continents;
    private Button delete;
    private Button edit;
    private int selected;
    public static int id;
    CharSequence[] items;
    private Map.Entry<String, String> entry;
    private String value;

    private byte[] photo;
    private ImageView courseImageView;
    ActivityEditOrDeleteAnExistingCourseBinding activityEditOrDeleteAnExistingCourseBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditOrDeleteAnExistingCourseBinding = ActivityEditOrDeleteAnExistingCourseBinding.inflate(getLayoutInflater());
        setContentView(activityEditOrDeleteAnExistingCourseBinding.getRoot());
        //setContentView(R.layout.activity_edit_or_delete_an_existing_course);
        listOfCourse = findViewById(R.id.list);
        delete = findViewById(R.id.Delete);
        edit = findViewById(R.id.Edit);
        courseNumber = findViewById(R.id.courseID);
        courseTitle = findViewById(R.id.courseTitle);
        courseMainTobic = findViewById(R.id.courseMainTobic);
        preRequest = findViewById(R.id.preRequest);
        courseImageView = findViewById(R.id.imageView4);
        dbHelper = new CourseDataBaseHelper(this);
        continents = dbHelper.getAllCourses();
        if (!(continents.isEmpty())) {
            listOfCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditOrDeleteAnExistingCourseActivity.this);
                    builder.setTitle("Courses :");
                    builder.setCancelable(false);

                    items = convertListToCharSequenceArray(continents);
                    builder.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selected = which;  // Update the selected continent index
                        }
                    });
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (selected < 0) {
                                Toast.makeText(EditOrDeleteAnExistingCourseActivity.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                            } else {
                                entry = continents.get(selected);
                                value = entry.getKey();
                                course = dbHelper.getCourseByID(Integer.parseInt(value));
                                id = Integer.parseInt(value);
                                courseNumber.setText(String.valueOf(course.getCourseID()));
                                courseTitle.setText(course.getCourseTitle());
                                courseMainTobic.setText(convertArrayListToString(course.getCourseMainTopics()));
                                preRequest.setText(convertArrayListToString(course.getPrerequisites()));
                                Bitmap Photo1 = dbHelper.getImage(course.getCourseTitle());
                                if(Photo1 != null) {
                                    //courseImageView = findViewById(R.id.imageView4);
                                    courseImageView.setImageBitmap(Photo1);
                                }
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dbHelper.deleteCourse(Integer.parseInt(value))) {
                        Toast.makeText(EditOrDeleteAnExistingCourseActivity.this, "This Courses Deleted Successfully.", Toast.LENGTH_SHORT).show();
                        continents.remove(selected);
                        courseTitle.setText("");
                        courseNumber.setText("");
                        courseMainTobic.setText("");
                        preRequest.setText("");

                    } else {
                        Toast.makeText(EditOrDeleteAnExistingCourseActivity.this, "This Courses Deleted Failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EditOrDeleteAnExistingCourseActivity.this, EditPageActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            Toast.makeText(EditOrDeleteAnExistingCourseActivity.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();
        }
    }

    public static String convertArrayListToString(ArrayList<String> arrayList) {
        return String.join(",", arrayList);
    }
    public static CharSequence[] convertListToCharSequenceArray(List<Map.Entry<String, String>> list) {
        CharSequence[] array = new CharSequence[list.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : list) {
            array[i++] = entry.getValue();
        }
        return array;
    }
}