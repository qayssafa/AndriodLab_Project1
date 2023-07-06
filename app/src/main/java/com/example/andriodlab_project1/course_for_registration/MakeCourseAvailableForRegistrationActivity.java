package com.example.andriodlab_project1.course_for_registration;

import static com.example.andriodlab_project1.course.CreateCourseActivity.convertListToCharSequenceArray;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity;
import com.example.andriodlab_project1.databinding.ActivityMakeCourseAvailabeForRegistrationBinding;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.notification.NotificationDataBaseHelper;
import com.example.andriodlab_project1.student.SearchAndViewCourseAreAvailableActivity;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class MakeCourseAvailableForRegistrationActivity extends DrawerBaseActivity {
    private List<Map.Entry<String, String>> continents;
    private AvailableCourseDataBaseHelper dbHelper;
    private CourseDataBaseHelper courseDataBaseHelper;
    private NotificationDataBaseHelper notificationDataBaseHelper;
    private StudentDataBaseHelper studentDataBaseHelper;
    private InstructorDataBaseHelper instructorDataBaseHelper;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;
    private List<Integer> allCourses;
    private int selected=0;
    private Button submit;
    private Map.Entry<String, String> entry;
    private String value;
    private CharSequence[] items;
    private boolean check;

    ActivityMakeCourseAvailabeForRegistrationBinding activityMakeCourseAvailabeForRegistrationBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMakeCourseAvailabeForRegistrationBinding = ActivityMakeCourseAvailabeForRegistrationBinding.inflate(getLayoutInflater());
        setContentView(activityMakeCourseAvailabeForRegistrationBinding.getRoot());
        //setContentView(R.layout.activity_make_course_availabe_for_registration);
        EditText Venue = (EditText) findViewById(R.id.VenueEditText);
        EditText CourseSchedule = (EditText) findViewById(R.id.CourseScheduleEditText);
        EditText InstructorName = (EditText) findViewById(R.id.InstructorNameEditText);
        TextView numberOfCourse = (TextView) findViewById(R.id.CourseNumberTextView);
        check=false;
        TextView listOfCourses = findViewById(R.id.listOfCourses);
        submit = findViewById(R.id.submit);
        //start date edit text
        EditText editStartDate = findViewById(R.id.StartDateEditText);
        EditText editEndDate = findViewById(R.id.CourseEndDate);
        EditText deadLine = findViewById(R.id.RegistrationDeadLineEditText);
        dbHelper = new AvailableCourseDataBaseHelper(this);
        courseDataBaseHelper = new CourseDataBaseHelper(this);
        notificationDataBaseHelper = new NotificationDataBaseHelper(this);
        SearchAndViewCourseAreAvailableActivity searchAndViewCourseAreAvailableActivity=new SearchAndViewCourseAreAvailableActivity();
        studentDataBaseHelper = new StudentDataBaseHelper(this);
        instructorDataBaseHelper = new InstructorDataBaseHelper(this);
        continents = courseDataBaseHelper.getAllCourses();


        if (!continents.isEmpty()){
            items = convertListToCharSequenceArray(continents);
            listOfCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MakeCourseAvailableForRegistrationActivity.this);
                builder.setTitle("Courses :");
                builder.setCancelable(false);
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
                     /*   while (selected==0){
                            Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This Course Number not Valid!", Toast.LENGTH_SHORT).show();
                        }*/
                        entry = continents.get(selected);
                        value = entry.getKey();
                        numberOfCourse.setText(value);
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
            editStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    // Launch Date Picker Dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MakeCourseAvailableForRegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in EditText
                            if (((monthOfYear + 1) > 9) && (dayOfMonth > 9)) {
                                editStartDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            } else if (((monthOfYear + 1) <= 9) && (dayOfMonth > 9))
                                editStartDate.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                            else if (((monthOfYear + 1) > 9) && (dayOfMonth <= 9)) {
                                editStartDate.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                            } else if ((((monthOfYear + 1) <= 9) && (dayOfMonth <= 9))) {
                                editStartDate.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                            }
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
            editEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    // Launch Date Picker Dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MakeCourseAvailableForRegistrationActivity.this,new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in EditText
                            if (((monthOfYear + 1) > 9) && (dayOfMonth > 9)) {
                                editEndDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            } else if (((monthOfYear + 1) <= 9) && (dayOfMonth > 9))
                                editEndDate.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                            else if (((monthOfYear + 1) > 9) && (dayOfMonth <= 9)) {
                                editEndDate.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                            } else if ((((monthOfYear + 1) <= 9) && (dayOfMonth <= 9))) {
                                editEndDate.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                            }

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        deadLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    // Launch Date Picker Dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MakeCourseAvailableForRegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in EditText
                            if (((monthOfYear + 1) > 9) && (dayOfMonth > 9)) {
                                deadLine.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            } else if (((monthOfYear + 1) <= 9) && (dayOfMonth > 9))
                                deadLine.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                            else if (((monthOfYear + 1) > 9) && (dayOfMonth <= 9)) {
                                deadLine.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                            } else if ((((monthOfYear + 1) <= 9) && (dayOfMonth <= 9))) {
                                deadLine.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                            }
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        } else {
            Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();
        }


        //Registration deadline
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=false;
                String venue=Venue.getText().toString();
                String courseSchedule=CourseSchedule.getText().toString();
                String instructorName=InstructorName.getText().toString();
                String lDeadLine=deadLine.getText().toString();
                String lEditStartDate=editStartDate.getText().toString();
                String lEditEndDate=editEndDate.getText().toString();
                String[] nameParts = instructorName.split(" ");
                String email=null;
                if (instructorName.isEmpty()||instructorName.isBlank()){
                    Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This instructorName not Valid!", Toast.LENGTH_SHORT).show();
                }
                else if (!(nameParts.length == 2)) {
                    Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "The Full Name for Instructor not Valid!", Toast.LENGTH_SHORT).show();
                }else {
                    String firstName = nameParts[0];
                    String lastName = nameParts[1];
                    email=instructorDataBaseHelper.getEmailByFullName(firstName,lastName);
                    if (email==null){
                        Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This Instructor not Valid!", Toast.LENGTH_SHORT).show();
                    }
                }
                if (lEditStartDate.isEmpty()||lEditStartDate.isBlank()){
                    Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This Start Date not Valid!", Toast.LENGTH_SHORT).show();
                }
                else if (lEditEndDate.isEmpty()||lEditEndDate.isBlank()){
                    Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This END Date not Valid!", Toast.LENGTH_SHORT).show();
                }else if (isEndDateAfterStartDate(lEditStartDate,lEditEndDate)){
                    Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This END Date And Start Date not Valid!", Toast.LENGTH_SHORT).show();
                }
                else if (lDeadLine.isEmpty()||lDeadLine.isBlank()){
                    Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This DeadLine Date not Valid!", Toast.LENGTH_SHORT).show();
                }
                else if (!isEndDateAfterStartDate(lEditStartDate,lDeadLine)){
                    Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This Start Date And DeadLine Date  not Valid!", Toast.LENGTH_SHORT).show();
                }
                else if (courseSchedule.isEmpty()||courseSchedule.isBlank()){
                    Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This courseSchedule not Valid!", Toast.LENGTH_SHORT).show();
                }
                else if (venue.isEmpty()||venue.isBlank()){
                    Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This Venue not Valid!", Toast.LENGTH_SHORT).show();
                } else{
                    allCourses = dbHelper.getAllCoursesAreCurrentTaughtByInstructor(email);
                    for (Integer courseId : allCourses) {
                        availableCourses = dbHelper.getAvailableCourseByCourse_Id(courseId);
                        for (Triple<AvailableCourse, String, Integer> lCourseInfo : availableCourses) {
                            AvailableCourse availableCourse = lCourseInfo.getFirst();
                            if (searchAndViewCourseAreAvailableActivity.isTimeConflict(availableCourse.getCourseSchedule(),courseSchedule)) {
                                Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This Course "+courseDataBaseHelper.getCourseName(availableCourse.getCourseId())+" Schedule its Conflict With this Instructor.", Toast.LENGTH_SHORT).show();
                                check=true;
                                break;
                            }
                        }
                        if (check)
                            break;
                    }
                    AvailableCourse availableCourse=new AvailableCourse(Integer.parseInt(value),lDeadLine,lEditStartDate,courseSchedule,venue,lEditEndDate);
                   if (email!=null&& !check) {
                       if (dbHelper.insertAvailableCourse(availableCourse, email, 0)) {
                           List<String> students = studentDataBaseHelper.getAllStudents();
                           for (String student : students) {
                               String message = "A New Course '" + courseDataBaseHelper.getCourseName(Integer.parseInt(value)) + "' has been Created.";
                               notificationDataBaseHelper.insertNotification(student, message);
                           }
                           Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This Course its Registration successfully.", Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This Course its Registration Failed.", Toast.LENGTH_SHORT).show();
                       }
                   }else {
                       Toast.makeText(MakeCourseAvailableForRegistrationActivity.this, "This Course its Registration Failed.", Toast.LENGTH_SHORT).show();
                   }
                }
            }
        });
    }
    public static boolean isEndDateAfterStartDate(String startDateString, String endDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateString,formatter);
        LocalDate endDate = LocalDate.parse(endDateString,formatter);

        return startDate.isAfter(endDate);
    }
}