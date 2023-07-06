package com.example.andriodlab_project1.student;

import android.content.DialogInterface;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.admin.Applicant;
import com.example.andriodlab_project1.admin.ApplicantDataBaseHelper;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.databinding.ActivityRegisterCourseBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class SearchAndViewCourseAreAvailableActivity extends StudentDrawerBaseActivity {

    private  Course course;
    private TableLayout tableLayout;

    private List<Map.Entry<String, String>> continents;
    private AvailableCourseDataBaseHelper dbHelper;
    private ApplicantDataBaseHelper applicantDataBaseHelper;
    private CourseDataBaseHelper courseDataBaseHelper;
    private int selected;
    private Map.Entry<String, String> entry;
    private String key;

    private CharSequence[] items;
    private TextView startDate;
    private TextView courseTitle;
    private TextView schedule;
    private TextView instructor;
    private TextView venue;
    private TextView endDate;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;
    private         List<Triple<AvailableCourse, String, Integer>> courseSelectedAv;

    ActivityRegisterCourseBinding activityRegisterCourseBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterCourseBinding = ActivityRegisterCourseBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterCourseBinding.getRoot());
        dbHelper=new AvailableCourseDataBaseHelper(this);
        applicantDataBaseHelper=new ApplicantDataBaseHelper(this);
        courseDataBaseHelper=new CourseDataBaseHelper(this);

        TextView listOfCourses = findViewById(R.id.enrollCoursesList);
        continents =dbHelper.getAllCoursesAreAvailableForRegistration();
        CharSequence[] items = convertListToCharSequenceArray(continents);
        Button enroll = findViewById(R.id.ENrollButton);
        if (!continents.isEmpty()) {
        listOfCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchAndViewCourseAreAvailableActivity.this);
                builder.setTitle("Courses Are Available For Registration :");
                builder.setCancelable(false);
                tableLayout = findViewById(R.id.student_message_table);
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
                                Toast.makeText(SearchAndViewCourseAreAvailableActivity.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                            } else {
                                entry = continents.get(selected);
                                key = entry.getKey();
                                course = courseDataBaseHelper.getCourseByID(Integer.parseInt(key));
                               courseSelectedAv=dbHelper.getAvailableCourseByCourse_Id(Integer.parseInt(key));
                               AvailableCourse availableCourse1=courseSelectedAv.get(0).getFirst();
                                int rowCount = tableLayout.getChildCount();
                                for (int i = rowCount - 1; i > 0; i--) {
                                    View childView = tableLayout.getChildAt(i);
                                    if (childView instanceof TableRow) {
                                        tableLayout.removeView(childView);
                                    }
                                }
                                TableRow row = new TableRow(tableLayout.getContext());
                                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(1, 1, 1, 1);
                                startDate = new TextView(row.getContext());
                                courseTitle = new TextView(row.getContext());
                                endDate = new TextView(row.getContext());
                                schedule = new TextView(row.getContext());
                                instructor = new TextView(row.getContext());
                                venue = new TextView(row.getContext());


                                courseTitle.setText(course.getCourseTitle());
                                courseTitle.setBackgroundColor(Color.WHITE);
                                courseTitle.setLayoutParams(layoutParams);
                                courseTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(courseTitle);

                                startDate.setText(availableCourse1.getCourseStartDate());
                                startDate.setBackgroundColor(Color.WHITE);
                                startDate.setLayoutParams(layoutParams);
                                startDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(startDate);

                                endDate.setText(availableCourse1.getCourseEndDate());
                                endDate.setBackgroundColor(Color.WHITE);
                                endDate.setLayoutParams(layoutParams);
                                endDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(endDate);

                                schedule.setText(availableCourse1.getCourseSchedule());
                                schedule.setBackgroundColor(Color.WHITE);
                                schedule.setLayoutParams(layoutParams);
                                schedule.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(schedule);

                                instructor.setText(courseSelectedAv.get(0).getSecond());
                                instructor.setBackgroundColor(Color.WHITE);
                                instructor.setLayoutParams(layoutParams);
                                instructor.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(instructor);

                                venue.setText(availableCourse1.getVenue());
                                venue.setBackgroundColor(Color.WHITE);
                                venue.setLayoutParams(layoutParams);
                                venue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(venue);

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
        }
        else {
            Toast.makeText(SearchAndViewCourseAreAvailableActivity.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();

        }
        enroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<String> prerequisites = course.getPrerequisites();
                List<Map.Entry<Integer, String>> getCoursesTakenByStudent=dbHelper.getCoursesTakenByStudent(MainActivity.studentEmail);
                availableCourses = dbHelper.getAvailableCourseByCourse_Id(course.getCourseID());
                String selectedCourseSchedule = null;
                String registeredCourseSchedule= null;;
                String selectedCourseScheduleName= null;;
                String registeredCourseScheduleName= null;;
                boolean check=false;
                if (!availableCourses.isEmpty()){
                    AvailableCourse availableCourse = availableCourses.get(0).getFirst();
                    AvailableCourse courseAreRegisteredByStudent;
                    selectedCourseSchedule=availableCourse.getCourseSchedule();
                    selectedCourseScheduleName=courseDataBaseHelper.getCourseName(availableCourse.getCourseId());
                    List<Map.Entry<Integer, String>> getCoursesAreRegisteredByStudent=dbHelper.getCoursesAreRegisteredByStudent(MainActivity.studentEmail);
                    List<Triple<AvailableCourse, String, Integer>> availableCoursesAreRegisteredByStudent;
                    for (Map.Entry<Integer, String> entry : getCoursesAreRegisteredByStudent) {
                        availableCoursesAreRegisteredByStudent = dbHelper.getAvailableCourseByCourse_Id(entry.getKey());
                        if (!availableCourses.isEmpty()) {
                            courseAreRegisteredByStudent=availableCoursesAreRegisteredByStudent.get(0).getFirst();
                            registeredCourseSchedule=courseAreRegisteredByStudent.getCourseSchedule();
                            if (isTimeConflict(selectedCourseSchedule,registeredCourseSchedule)){
                                check=true;
                                registeredCourseScheduleName=courseDataBaseHelper.getCourseName(courseAreRegisteredByStudent.getCourseId());
                                break;
                            }
                        }
                    }
                    }
                if (!check){
                    if (arePrerequisitesMet(prerequisites,getCoursesTakenByStudent)){
                        Applicant applicant=new Applicant(course.getCourseID(),MainActivity.studentEmail," ");
                        applicantDataBaseHelper.insertApplicant(applicant);
                        Toast.makeText(SearchAndViewCourseAreAvailableActivity.this,"Wait Until Admin Accept Your Request.", Toast.LENGTH_SHORT).show();
                        //no conflict in time
                    }else {
                        Toast.makeText(SearchAndViewCourseAreAvailableActivity.this,"Cannot Registered this course "+ courseDataBaseHelper.getCourseName(course.getCourseID())
                                +"\n because your not completed Prerequisites Courses for this course "+courseDataBaseHelper.getCourseName(course.getCourseID())+".", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SearchAndViewCourseAreAvailableActivity.this,"Cannot Registered this course "+ selectedCourseScheduleName +"\n because its Conflict in time with your course "+registeredCourseScheduleName+".", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static String convertArrayListToString(ArrayList<String> arrayList) {
        return String.join("\n", arrayList);
    }
    public CharSequence[] convertListToCharSequenceArray(List<Map.Entry<String, String>> list) {
        CharSequence[] array = new CharSequence[list.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : list) {
            array[i++] = courseDataBaseHelper.getCourseName(Integer.parseInt(entry.getKey()));
        }
        return array;
    }
    public boolean arePrerequisitesMet(List<String> prerequisites, List<Map.Entry<Integer, String>> getCoursesTakenByStudent) {
        if (prerequisites.isEmpty()|| prerequisites.get(0).isBlank()){
            return true;
        }
        List<String> courseNames = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : getCoursesTakenByStudent) {
            String courseName = entry.getValue();
            courseNames.add(courseName);
        }
        for (String prerequisite : prerequisites) {
            boolean prerequisiteMet = false;
            for (String courseTaken : courseNames) {
                if (prerequisite.equals(courseTaken)) {
                    prerequisiteMet = true;
                    break;
                }
            }
            if (!prerequisiteMet) {
                return false;
            }
        }
        return true;
    }
    public boolean isTimeConflict(String time1, String time2) {
        // Extract information from time1
        String[] parts1 = time1.split(" ");
        String[] dayParts1 = parts1[0].split(",");
        String days1 = dayParts1.length > 1 ? dayParts1[0] + "," + dayParts1[1] : dayParts1[0];
        String timeRange1 = parts1[1];
        String[] timeParts1 = timeRange1.split("-");
        String startTime1 = timeParts1[0].trim();
        String endTime1 = timeParts1[1].trim();

        // Extract information from time2
        String[] parts2 = time2.split(" ");
        String[] dayParts2 = parts2[0].split(",");
        String days2 = dayParts2.length > 1 ? dayParts2[0] + "," + dayParts2[1] : dayParts2[0];
        String timeRange2 = parts2[1];
        String[] timeParts2 = timeRange2.split("-");
        String startTime2 = timeParts2[0].trim();
        String endTime2 = timeParts2[1].trim();


        String[] dayArray1 = days1.split(",");
        String[] dayArray2 = days2.split(",");

        // Check for conflicts between days and time ranges
        for (String day1 : dayArray1) {
            for (String day2 : dayArray2) {
                if (day1.equals(day2)) {
                    // Days match, check for time conflict
                    if (checkIsTimeConflict(startTime1, endTime1, startTime2, endTime2)) {
                        // There is a conflict, both day and time overlap
                        return true;
                    }
                }
            }
        }

        // No time conflict
        return false;
    }

    public boolean checkIsTimeConflict(String startTime1, String endTime1, String startTime2, String endTime2) {
        int startMinutes1 = convertToMinutes(startTime1);
        int endMinutes1 = convertToMinutes(endTime1);
        int startMinutes2 = convertToMinutes(startTime2);
        int endMinutes2 = convertToMinutes(endTime2);

        // Check for time conflict
        if ((startMinutes1 <= startMinutes2 && startMinutes2 <= endMinutes1) || // Case 1
                (startMinutes1 <= endMinutes2 && endMinutes2 <= endMinutes1) ||     // Case 2
                (startMinutes2 <= startMinutes1 && startMinutes1 <= endMinutes2) || // Case 3
                (startMinutes2 <= endMinutes1 && endMinutes1 <= endMinutes2)) {     // Case 4
            return true; // There is a time conflict
        }


        return false; // No time conflict
    }


    public int convertToMinutes(String time) {
        String[] parts = time.split(":");
        int hours;
        int minutes;

        if (time.contains("AM") || time.contains("PM")) {
            String[] timeParts = parts[1].split("(?<=\\d)(?=\\p{Upper})");
            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(timeParts[0]);

            if (timeParts[1].equalsIgnoreCase("PM") && hours < 12) {
                hours += 12;
            }
        } else {
            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);
        }

        return hours * 60 + minutes;
    }



}
