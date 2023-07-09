package com.example.andriodlab_project1.student;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity;
import com.example.andriodlab_project1.course.EditPageActivity;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.databinding.ActivityCourseWithdrawBinding;
import com.example.andriodlab_project1.enrollment.EnrollmentDataBaseHelper;

import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class CourseWithdrawActivity extends StudentDrawerBaseActivity {

    private EditText ReasonsString;
    private Button WithdrawButton;

    private Course course;

    private List<Map.Entry<Integer, String>> continents;
    private EnrollmentDataBaseHelper dbHelper;
    private CourseDataBaseHelper courseDataBaseHelper;
    private int selected;
    private Map.Entry<Integer, String> entry;
    ActivityCourseWithdrawBinding activityCourseWithdrawBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCourseWithdrawBinding = ActivityCourseWithdrawBinding.inflate(getLayoutInflater());
        setContentView(activityCourseWithdrawBinding.getRoot());

        ReasonsString = (EditText) findViewById(R.id.Reasons);
        WithdrawButton = (Button) findViewById(R.id.SubmitWithdraw);

        AvailableCourseDataBaseHelper availableCourseDataBaseHelper = new AvailableCourseDataBaseHelper(this);

        courseDataBaseHelper = new CourseDataBaseHelper(this);
        continents = availableCourseDataBaseHelper.getCoursesAreRegisteredByStudent(MainActivity.studentEmail);
        CharSequence[] items = convertListToCharSequenceArray(continents);

        if (!continents.isEmpty()) {
            ReasonsString.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseWithdrawActivity.this);
                builder.setTitle("Courses Are Available To Withdraw It :");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(items, selected, (dialog, which) -> {
                    selected = which;  // Update the selected continent index
                });

                builder.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    if (selected < 0) {
                        Toast.makeText(CourseWithdrawActivity.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                    } else {
                        entry = continents.get(selected);
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                builder.show();
            });
        } else {
            Toast.makeText(CourseWithdrawActivity.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();

        }
        WithdrawButton.setOnClickListener(v -> {
            if (dbHelper.deleteEnrollment(entry.getKey(), MainActivity.studentEmail)) ;
            Toast.makeText(CourseWithdrawActivity.this, "Withdraw Operation Done.", Toast.LENGTH_SHORT).show();


        });
    }

    public CharSequence[] convertListToCharSequenceArray(List<Map.Entry<Integer, String>> list) {
        CharSequence[] array = new CharSequence[list.size()];
        int i = 0;
        for (Map.Entry<Integer, String> entry : list) {
            array[i++] = entry.getValue();
        }
        return array;
    }

}
