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
import android.widget.Toast;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity;
import com.example.andriodlab_project1.databinding.ActivityViewPreviousOfferingsBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kotlin.Triple;

public class ViewPreviousOfferings extends DrawerBaseActivity {
    private CourseDataBaseHelper dbHelper;
    private AvailableCourseDataBaseHelper availableCourseDataBaseHelper;
    private List<Map.Entry<String, String>> continents;
    private int selected;
    private CharSequence[] getItems;
    private Map.Entry<String, String> entry;
    private String value;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;
    private TableLayout tableLayout;

    private TextView courseNumber;
    private TextView courseTitle;
    private TextView date;
    private TextView time;
    private TextView numberOfStudents;
    private TextView venue;
    private TextView instructorName;

    ActivityViewPreviousOfferingsBinding activityViewPreviousOfferingsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewPreviousOfferingsBinding = ActivityViewPreviousOfferingsBinding.inflate(getLayoutInflater());
        setContentView(activityViewPreviousOfferingsBinding.getRoot());
        TextView listOfCourses = findViewById(R.id.newlist);
        dbHelper = new CourseDataBaseHelper(this);
        availableCourseDataBaseHelper = new AvailableCourseDataBaseHelper(this);
        continents = availableCourseDataBaseHelper.getAllCoursesForRegistration();
        tableLayout = findViewById(R.id.table);

        if (!continents.isEmpty()) {
            listOfCourses.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewPreviousOfferings.this);
                builder.setTitle("Courses :");
                builder.setCancelable(false);
                getItems = convertListToCharSequenceArray(continents);

                builder.setSingleChoiceItems(getItems, selected, (dialog, which) -> {
                    selected = which;  // Update the selected continent index
                });
                builder.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    int rowCount = tableLayout.getChildCount();
                    for (int i = rowCount - 1; i > 0; i--) {
                        View childView = tableLayout.getChildAt(i);
                        if (childView instanceof TableRow) {
                            tableLayout.removeView(childView);
                        }
                    }
                    entry = continents.get(selected);
                    value = entry.getKey();
                    availableCourses = availableCourseDataBaseHelper.getAvailableCourseByCourse_Id(Integer.parseInt(value));
                    for (Triple<AvailableCourse, String, Integer> courseInfo : availableCourses) {
                        AvailableCourse availableCourse = courseInfo.getFirst();
                        String linstructorName = courseInfo.getSecond();
                        Integer lnumberOfStudents = courseInfo.getThird();
                        TableRow row = new TableRow(tableLayout.getContext());
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(1, 1, 1, 1);
                        courseNumber = new TextView(row.getContext());
                        courseTitle = new TextView(row.getContext());
                        date = new TextView(row.getContext());
                        time = new TextView(row.getContext());
                        numberOfStudents = new TextView(row.getContext());
                        venue = new TextView(row.getContext());
                        instructorName = new TextView(row.getContext());
                        courseNumber.setText(String.valueOf(availableCourse.getCourseId()));
                        courseNumber.setBackgroundColor(Color.WHITE);
                        courseNumber.setLayoutParams(layoutParams);
                        courseNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(courseNumber);

                        Course course = dbHelper.getCourseByID(availableCourse.getCourseId());
                        courseTitle.setText(course.getCourseTitle());
                        courseTitle.setBackgroundColor(Color.WHITE);
                        courseTitle.setLayoutParams(layoutParams);
                        courseTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(courseTitle);

                        date.setText(" " + availableCourse.getCourseStartDate() + " To " + availableCourse.getRegistrationDeadline() + " ");
                        date.setBackgroundColor(Color.WHITE);
                        date.setLayoutParams(layoutParams);
                        date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(date);

                        time.setText(availableCourse.getCourseSchedule());
                        time.setBackgroundColor(Color.WHITE);
                        time.setLayoutParams(layoutParams);
                        time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        ;
                        row.addView(time);


                        numberOfStudents.setText(lnumberOfStudents + "");
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
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                builder.show();
            });
        } else {
            Toast.makeText(ViewPreviousOfferings.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();
        }
    }

    public static CharSequence[] convertListToCharSequenceArray(List<Map.Entry<String, String>> list) {
        Set<String> uniqueValues = new HashSet<>();
        List<CharSequence> uniqueList = new ArrayList<>();

        for (Map.Entry<String, String> entry : list) {
            String value = entry.getValue();
            if (!uniqueValues.contains(value)) {
                uniqueValues.add(value);
                uniqueList.add(value);
            }
        }

        return uniqueList.toArray(new CharSequence[0]);
    }
}