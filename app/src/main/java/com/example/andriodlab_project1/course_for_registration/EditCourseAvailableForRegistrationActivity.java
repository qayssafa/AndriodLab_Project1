package com.example.andriodlab_project1.course_for_registration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.databinding.ActivityEditCourseAvailabeForRegistrationBinding;
import com.example.andriodlab_project1.enrollment.EnrollmentDataBaseHelper;
import com.example.andriodlab_project1.instructor.Instructor;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.notification.NotificationDataBaseHelper;
import com.example.andriodlab_project1.student.SearchAndViewCourseAreAvailableActivity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class EditCourseAvailableForRegistrationActivity extends DrawerBaseActivity {

    private EditText InstructorName;

    private EditText CourseSchedule;
    private EditText Venue;
    private TextView CourseNumber;

    private List<Map.Entry<Integer, Integer>> continents;
    private AvailableCourseDataBaseHelper dbHelper;
    private EnrollmentDataBaseHelper enrollmentDataBaseHelper;
    private CourseDataBaseHelper courseDataBaseHelper;
    private NotificationDataBaseHelper notificationDataBaseHelper;
    private Instructor instructor;
    private InstructorDataBaseHelper instructorDataBaseHelper;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;
    private List<Integer> allCourses;
    private int selected = 0;
    private int key = 0;
    private Map.Entry<Integer, Integer> entry;
    private int value;
    private CharSequence[] items;
    private boolean check;
    private boolean checkInstructor;

    ActivityEditCourseAvailabeForRegistrationBinding activityEditCourseAvailabeForRegistrationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditCourseAvailabeForRegistrationBinding = ActivityEditCourseAvailabeForRegistrationBinding.inflate(getLayoutInflater());
        setContentView(activityEditCourseAvailabeForRegistrationBinding.getRoot());
        //setContentView(R.layout.activity_edit_course_availabe_for_registration);

        InstructorName = findViewById(R.id.InstructorNameEditTextEdit);
        EditText editStartDate = (EditText) findViewById(R.id.StartDateEditTextEdit);
        EditText editEndDate = (EditText) findViewById(R.id.CourseEndDateEdit);
        EditText deadLine = (EditText) findViewById(R.id.RegistrationDeadLineEditTextEdit);
        CourseSchedule = (EditText) findViewById(R.id.CourseScheduleEditTextEdit);
        Venue = (EditText) findViewById(R.id.VenueEditTextEdit);
        CourseNumber = (TextView) findViewById(R.id.CourseNumberTextViewEdit);
        Button submit = findViewById(R.id.submitEdit);

        check = false;
        checkInstructor = false;
        TextView listOfCourses = findViewById(R.id.listOfCoursesUpdate);
        dbHelper = new AvailableCourseDataBaseHelper(this);
        enrollmentDataBaseHelper = new EnrollmentDataBaseHelper(this);
        courseDataBaseHelper = new CourseDataBaseHelper(this);
        notificationDataBaseHelper = new NotificationDataBaseHelper(this);
        SearchAndViewCourseAreAvailableActivity searchAndViewCourseAreAvailableActivity = new SearchAndViewCourseAreAvailableActivity();
        instructorDataBaseHelper = new InstructorDataBaseHelper(this);
        continents = dbHelper.getAllCoursesAreAvailableForRegistrationWithRegId();
        instructor = new Instructor();


        if (!continents.isEmpty()) {
            items = convertListToCharSequenceArray(continents);
            listOfCourses.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseAvailableForRegistrationActivity.this);
                builder.setTitle("Courses :");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(items, selected, (dialog, which) -> {
                    selected = which;  // Update the selected continent index
                });
                builder.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    entry = continents.get(selected);
                    key = entry.getKey();
                    value=entry.getValue();
                    CourseNumber.setText(String.valueOf(value));
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                builder.show();
            });
            editStartDate.setOnClickListener(view -> {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditCourseAvailableForRegistrationActivity.this, (view13, year, monthOfYear, dayOfMonth) -> {
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
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            });
            editEndDate.setOnClickListener(view -> {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditCourseAvailableForRegistrationActivity.this, (view12, year, monthOfYear, dayOfMonth) -> {
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

                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            });
            deadLine.setOnClickListener(view -> {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditCourseAvailableForRegistrationActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
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
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            });
        } else {
            Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "No Courses Are found.", Toast.LENGTH_SHORT).show();
        }
        submit.setOnClickListener(v -> {
            check = false;
            checkInstructor = false;
            String venue = Venue.getText().toString();
            String courseSchedule = CourseSchedule.getText().toString();
            String instructorName = InstructorName.getText().toString();
            String lDeadLine = deadLine.getText().toString();
            String lEditStartDate = editStartDate.getText().toString();
            String lEditEndDate = editEndDate.getText().toString();
            String[] nameParts = instructorName.split(" ");
            String email = null;
            if (instructorName.isEmpty() || instructorName.isBlank()) {
                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This instructorName not Valid!", Toast.LENGTH_SHORT).show();
            } else if (!(nameParts.length == 2)) {
                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "The Full Name for Instructor not Valid!", Toast.LENGTH_SHORT).show();
            } else {
                String firstName = nameParts[0];
                String lastName = nameParts[1];
                email = instructorDataBaseHelper.getEmailByFullName(firstName, lastName);
                if (email == null) {
                    Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This Instructor not Valid!", Toast.LENGTH_SHORT).show();
                }
            }
            if (lEditStartDate.isEmpty() || lEditStartDate.isBlank()) {
                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This Start Date not Valid!", Toast.LENGTH_SHORT).show();
            } else if (lEditEndDate.isEmpty() || lEditEndDate.isBlank()) {
                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This END Date not Valid!", Toast.LENGTH_SHORT).show();
            } else if (isEndDateAfterStartDate(lEditStartDate, lEditEndDate)) {
                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This END Date And Start Date not Valid!", Toast.LENGTH_SHORT).show();
            } else if (lDeadLine.isEmpty() || lDeadLine.isBlank()) {
                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This DeadLine Date not Valid!", Toast.LENGTH_SHORT).show();
            } else if (!isEndDateAfterStartDate(lEditStartDate, lDeadLine)) {
                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This Start Date And DeadLine Date  not Valid!", Toast.LENGTH_SHORT).show();
            } else if (courseSchedule.isEmpty() || courseSchedule.isBlank()) {
                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This courseSchedule not Valid!", Toast.LENGTH_SHORT).show();
            } else if (venue.isEmpty() || venue.isBlank()) {
                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This Venue not Valid!", Toast.LENGTH_SHORT).show();
            } else {
                if (email != null) {
                    instructor = instructorDataBaseHelper.getInstructorByEmail(email);
                    List<String> coursesTaughtByInstructor = instructor.getCoursesTaught();
                    for (String course : coursesTaughtByInstructor) {
                        if (course.equals(courseDataBaseHelper.getCourseName(value))) {
                            checkInstructor = true;
                            break;
                        }
                    }
                    if (checkInstructor) {
                        allCourses = dbHelper.getAllCoursesAreCurrentTaughtByInstructor(email);
                        for (Integer courseId : allCourses) {
                            availableCourses = dbHelper.getAvailableCourseByCourse_Id(courseId);
                            for (Triple<AvailableCourse, String, Integer> lCourseInfo : availableCourses) {
                                AvailableCourse availableCourse = lCourseInfo.getFirst();
                                if (searchAndViewCourseAreAvailableActivity.isTimeConflict(availableCourse.getCourseSchedule(), courseSchedule)&&(value!=availableCourse.getCourseId())) {
                                    Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This  Course Are you Registration it " + courseDataBaseHelper.getCourseName(availableCourse.getCourseId()) + " Schedule its Conflict With this Instructor.", Toast.LENGTH_SHORT).show();
                                    check = true;
                                    break;
                                }
                            }
                            if (check)
                                break;
                        }
                        AvailableCourse availableCourse = new AvailableCourse(value, lDeadLine, lEditStartDate, courseSchedule, venue, lEditEndDate);
                        availableCourse.setReg(key);
                        if (!check) {
                            if (dbHelper.updateAvailableCourse(availableCourse, email)) {
                                List<String> students = enrollmentDataBaseHelper.getStudentsByCourseId(value);
                                for (String student : students) {
                                    String message = "This Course '" + courseDataBaseHelper.getCourseByID(value) + "' has been Updated.";
                                    notificationDataBaseHelper.insertNotification(student, message);
                                }
                                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This Course its Updated successfully.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This Course its Updated Failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This Course its Updated Failed.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This Instructor" + instructor.getFirstName() + instructor.getLastName() + " Not Taught This Course.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This Course its Updated Failed.", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(EditCourseAvailableForRegistrationActivity.this, "This Course its Updated Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static boolean isEndDateAfterStartDate(String startDateString, String endDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = LocalDate.parse(endDateString, formatter);

        return startDate.isAfter(endDate);
    }

    public  CharSequence[] convertListToCharSequenceArray(List<Map.Entry<Integer, Integer>> list) {
        CharSequence[] array = new CharSequence[list.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : list) {
            array[i++] = courseDataBaseHelper.getCourseName(entry.getValue());
        }
        return array;
    }

}
