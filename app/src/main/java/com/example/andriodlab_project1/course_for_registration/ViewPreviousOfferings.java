package com.example.andriodlab_project1.course_for_registration;

import static com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity.convertListToCharSequenceArray;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity;

import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class ViewPreviousOfferings extends AppCompatActivity {
    private     CharSequence[] items;
    private CourseDataBaseHelper dbHelper;
    private AvailableCourseDataBaseHelper availableCourseDataBaseHelper;
    private List<Map.Entry<String, String>> continents;
    private int selected;
    private CharSequence[] getItems;
    private Map.Entry<String, String> entry;
    private String value;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;
    private TableLayout tableLayout;
    private TableRow row;
    private TextView listOfCourses;
    private TextView courseNumber;
    private TextView courseTitle;
    private TextView date;
    private TextView  time;
    private TextView numberOfStudents;
    private TextView venue;
    private TextView instructorName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_previous_offerings);
        listOfCourses=findViewById(R.id.newlist);
        dbHelper = new CourseDataBaseHelper(this);
        availableCourseDataBaseHelper = new AvailableCourseDataBaseHelper(this);
        continents = dbHelper.getAllCourses();
        tableLayout = findViewById(R.id.table);
        row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(1, 1, 1, 1);
        courseNumber = new TextView(this);
        courseTitle = new TextView(this);
        date = new TextView(this);
        time = new TextView(this);
        numberOfStudents = new TextView(this);
        venue = new TextView(this);
        instructorName = new TextView(this);

        listOfCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewPreviousOfferings.this);
                builder.setTitle("Courses :");
                builder.setCancelable(false);
                getItems = convertListToCharSequenceArray(continents);

                builder.setSingleChoiceItems(getItems,selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = which;  // Update the selected continent index
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        entry = continents.get(selected);
                        value = entry.getKey();
                        availableCourses= availableCourseDataBaseHelper.getAvailableCourseByCourse_Id(Integer.parseInt(value));
                        for (Triple<AvailableCourse, String, Integer> courseInfo : availableCourses) {
                            AvailableCourse availableCourse = courseInfo.getFirst();
                            String linstructorName = courseInfo.getSecond();
                            Integer lnumberOfStudents = courseInfo.getThird();

                            courseNumber.setText(String.valueOf(availableCourse.getCourseId()));
                            courseNumber.setBackgroundColor(Color.WHITE);
                            courseNumber.setLayoutParams(layoutParams);
                            courseNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            row.addView(courseNumber);

                            Course course=dbHelper.getCourseByID(availableCourse.getCourseId());
                            courseTitle.setText(course.getCourseTitle());
                            courseTitle.setBackgroundColor(Color.WHITE);
                            courseTitle.setLayoutParams(layoutParams);
                            courseTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            row.addView(courseTitle);

                            date.setText(" "+availableCourse.getCourseStartDate()+" To "+availableCourse.getRegistrationDeadline()+" ");
                            date.setBackgroundColor(Color.WHITE);
                            date.setLayoutParams(layoutParams);
                            date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            row.addView(date);

                            time.setText(availableCourse.getCourseSchedule());
                            time.setBackgroundColor(Color.WHITE);
                            time.setLayoutParams(layoutParams);
                            time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);;
                            row.addView(time);


                            numberOfStudents.setText(lnumberOfStudents+"");
                            numberOfStudents.setBackgroundColor(Color.WHITE);
                            numberOfStudents.setLayoutParams(layoutParams);
                            numberOfStudents.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            row.addView(numberOfStudents);

                            venue.setText(availableCourse.getVenue());
                            venue.setBackgroundColor(Color.WHITE);
                            venue.setLayoutParams(layoutParams);
                            venue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            row.addView(venue);

                            instructorName.setText(linstructorName);
                            instructorName.setBackgroundColor(Color.WHITE);
                            instructorName.setLayoutParams(layoutParams);
                            instructorName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            row.addView(instructorName);


                            tableLayout.addView(row);

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

        // Add the TableRow to the TableLayout
    }
}