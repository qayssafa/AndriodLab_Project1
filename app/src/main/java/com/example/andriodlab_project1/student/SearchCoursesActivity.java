package com.example.andriodlab_project1.student;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.databinding.ActivitySearchCoursesBinding;

import java.util.List;
import java.util.Map;
public class SearchCoursesActivity extends StudentDrawerBaseActivity {
    private  Course course;
    private TableLayout tableLayout;

    private List<Map.Entry<String, String>> continents;
    private AvailableCourseDataBaseHelper dbHelper;
    private CourseDataBaseHelper courseDataBaseHelper;
    private int selected;
    private Map.Entry<String, String> entry;
    private String key;
    private TextView courseNumber;
    private TextView courseTitle;
    private TextView preRequest;

    private TextView mainTopics;

    ActivitySearchCoursesBinding activitySearchCoursesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activitySearchCoursesBinding = ActivitySearchCoursesBinding.inflate(getLayoutInflater());
        setContentView(activitySearchCoursesBinding.getRoot());
//        setContentView(R.layout.activity_search_courses);
        TextView newlistToSelect = findViewById(R.id.newlistToSelect);
        dbHelper=new AvailableCourseDataBaseHelper(this);
        courseDataBaseHelper=new CourseDataBaseHelper(this);
        continents =dbHelper.getAllCoursesAreAvailableForRegistration();
        CharSequence[] items = convertListToCharSequenceArray(continents);

        if (!continents.isEmpty()) {
            newlistToSelect.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchCoursesActivity.this);
                builder.setTitle("Courses Are Available For Registration :");
                builder.setCancelable(false);
                tableLayout = findViewById(R.id.tableToSearchCourses);
                builder.setSingleChoiceItems(items, selected, (dialog, which) -> {
                    selected = which;  // Update the selected continent index
                });

                builder.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    if (selected < 0) {
                        Toast.makeText(SearchCoursesActivity.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                    } else {
                        entry = continents.get(selected);
                        key = entry.getKey();
                        course = courseDataBaseHelper.getCourseByID(Integer.parseInt(key));

                        TableRow row = new TableRow(tableLayout.getContext());
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(1, 1, 1, 1);

                        int rowCount = tableLayout.getChildCount();
                        for (int i = rowCount - 1; i > 0; i--) {
                            View childView = tableLayout.getChildAt(i);
                            if (childView instanceof TableRow) {
                                tableLayout.removeView(childView);
                            }
                        }

                        courseNumber = new TextView(row.getContext());
                        courseTitle = new TextView(row.getContext());
                        mainTopics = new TextView(row.getContext());
                        preRequest = new TextView(row.getContext());

                        courseNumber.setText(String.valueOf(course.getCourseID()));
                        courseNumber.setBackgroundColor(Color.WHITE);
                        courseNumber.setLayoutParams(layoutParams);
                        courseNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(courseNumber);

                        courseTitle.setText(course.getCourseTitle());
                        courseTitle.setBackgroundColor(Color.WHITE);
                        courseTitle.setLayoutParams(layoutParams);
                        courseTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(courseTitle);



                        mainTopics.setText(convertListToString(course.getCourseMainTopics()));
                        mainTopics.setBackgroundColor(Color.WHITE);
                        mainTopics.setLayoutParams(layoutParams);
                        mainTopics.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(mainTopics);

                        preRequest.setText(convertListToString(course.getPrerequisites()));
                        preRequest.setBackgroundColor(Color.WHITE);
                        preRequest.setLayoutParams(layoutParams);
                        preRequest.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(preRequest);


                        tableLayout.addView(row);
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                builder.show();
            });
        } else {
            Toast.makeText(SearchCoursesActivity.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();

        }

    }
    public String convertListToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String element : list) {
            sb.append(element).append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // Remove the last comma
        }
        return sb.toString();
    }
    public CharSequence[] convertListToCharSequenceArray(List<Map.Entry<String, String>> list) {
        CharSequence[] array = new CharSequence[list.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : list) {
            array[i++] = courseDataBaseHelper.getCourseName(Integer.parseInt(entry.getKey()));
        }
        return array;
    }
}