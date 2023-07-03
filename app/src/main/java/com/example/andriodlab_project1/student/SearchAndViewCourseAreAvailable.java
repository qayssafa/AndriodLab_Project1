package com.example.andriodlab_project1.student;

import static com.example.andriodlab_project1.course.CreateCourseActivity.convertListToCharSequenceArray;

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
import androidx.appcompat.app.AppCompatActivity;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.admin.Applicant;
import com.example.andriodlab_project1.admin.ApplicantDataBaseHelper;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.ViewPreviousOfferings;
import com.example.andriodlab_project1.databinding.ActivityRegisterCourseBinding;
import com.example.andriodlab_project1.databinding.ActivityStudentMainBinding;
import com.example.andriodlab_project1.enrollment.Enrollment;
import com.example.andriodlab_project1.enrollment.EnrollmentDataBaseHelper;
import com.example.andriodlab_project1.notification.NotificationDataBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class SearchAndViewCourseAreAvailable extends StudentDrawerBaseActivity {

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
    private TextView courseNumber;
    private TextView courseTitle;
    private TextView courseMainTopic;
    private TextView time;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchAndViewCourseAreAvailable.this);
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
                                Toast.makeText(SearchAndViewCourseAreAvailable.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                            } else {
                                entry = continents.get(selected);
                                key = entry.getKey();
                                course = courseDataBaseHelper.getCourseByID(Integer.parseInt(key));
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
                                courseNumber = new TextView(row.getContext());
                                courseTitle = new TextView(row.getContext());
                                time = new TextView(row.getContext());
                                courseMainTopic = new TextView(row.getContext());

                                courseNumber.setText(course.getCourseID() + "");
                                courseNumber.setBackgroundColor(Color.WHITE);
                                courseNumber.setLayoutParams(layoutParams);
                                courseNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(courseNumber);

                                courseTitle.setText(course.getCourseTitle());
                                courseTitle.setBackgroundColor(Color.WHITE);
                                courseTitle.setLayoutParams(layoutParams);
                                courseTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(courseTitle);

                                time.setText(entry.getValue());
                                time.setBackgroundColor(Color.WHITE);
                                time.setLayoutParams(layoutParams);
                                time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(time);

                                courseMainTopic.setText(convertArrayListToString(course.getCourseMainTopics()));
                                courseMainTopic.setBackgroundColor(Color.WHITE);
                                courseMainTopic.setLayoutParams(layoutParams);
                                courseMainTopic.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(courseMainTopic);

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
            Toast.makeText(SearchAndViewCourseAreAvailable.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();

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
                    selectedCourseScheduleName=CourseDataBaseHelper.getCourseName(availableCourse.getCourseId());
                    List<Map.Entry<Integer, String>> getCoursesAreRegisteredByStudent=dbHelper.getCoursesAreRegisteredByStudent(MainActivity.studentEmail);
                    List<Triple<AvailableCourse, String, Integer>> availableCoursesAreRegisteredByStudent;
                    for (Map.Entry<Integer, String> entry : getCoursesAreRegisteredByStudent) {
                        availableCoursesAreRegisteredByStudent = dbHelper.getAvailableCourseByCourse_Id(entry.getKey());
                        if (!availableCourses.isEmpty()) {
                            courseAreRegisteredByStudent=availableCoursesAreRegisteredByStudent.get(0).getFirst();
                            registeredCourseSchedule=courseAreRegisteredByStudent.getCourseSchedule();
                            if (isTimeConflict(selectedCourseSchedule,registeredCourseSchedule)){
                                check=true;
                                registeredCourseScheduleName=CourseDataBaseHelper.getCourseName(courseAreRegisteredByStudent.getCourseId());
                                break;
                            }
                        }
                    }
                    }
                if (!check){
                    if (arePrerequisitesMet(prerequisites,getCoursesTakenByStudent)){
                        Applicant applicant=new Applicant(course.getCourseID(),MainActivity.studentEmail,"NO");
                        applicantDataBaseHelper.insertApplicant(applicant);
                        Toast.makeText(SearchAndViewCourseAreAvailable.this,"Wait Until Admin Accept Your Request.", Toast.LENGTH_SHORT).show();
                        //no conflict in time
                    }else {
                        Toast.makeText(SearchAndViewCourseAreAvailable.this,"Cannot Registered this course "+ CourseDataBaseHelper.getCourseName(course.getCourseID())
                                +"\n because your not completed Prerequisites Courses for this course "+CourseDataBaseHelper.getCourseName(course.getCourseID())+".", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SearchAndViewCourseAreAvailable.this,"Cannot Registered this course "+ selectedCourseScheduleName +"\n because its Conflict in time with your course "+registeredCourseScheduleName+".", Toast.LENGTH_SHORT).show();
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
            array[i++] = CourseDataBaseHelper.getCourseName(Integer.parseInt(entry.getKey()));
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
    private boolean isTimeConflict(String time1, String time2) {
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
                    if (isTimeConflict(startTime1, endTime1, startTime2, endTime2)) {
                        // There is a conflict, both day and time overlap
                        return true;
                    }
                }
            }
        }

        // No time conflict
        return false;
    }

    private boolean isTimeConflict(String startTime1, String endTime1, String startTime2, String endTime2) {
        int startMinutes1 = convertToMinutes(startTime1);
        int endMinutes1 = convertToMinutes(endTime1);
        int startMinutes2 = convertToMinutes(startTime2);
        int endMinutes2 = convertToMinutes(endTime2);

        // Check for time conflict
        if (startMinutes1 <= endMinutes2 && startMinutes2 <= endMinutes1) {
            return true;
        }

        // No time conflict
        return false;
    }

    private int convertToMinutes(String time) {
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
