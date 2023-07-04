package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.databinding.ActivityApplicantDecideBinding;
import com.example.andriodlab_project1.enrollment.Enrollment;
import com.example.andriodlab_project1.enrollment.EnrollmentDataBaseHelper;
import com.example.andriodlab_project1.notification.NotificationDataBaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.Triple;

public class ApplicantDecide extends DrawerBaseActivity {

    private CheckBox checkboxAccept;
    private CheckBox checkboxReject;
    private TableLayout tableLayout;
    private TextView Applicant_id;
    private TextView course_id;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;
    private int key;
    private int selected=0;
    private TextView email;
    private TextView listOfApplicant;
    private ApplicantDataBaseHelper applicantDataBaseHelper;
    private EnrollmentDataBaseHelper enrollmentDataBaseHelper;
    private  List<Integer>  items;
    private AvailableCourseDataBaseHelper dbHelper;
    private NotificationDataBaseHelper notificationDataBaseHelper;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private CourseDataBaseHelper courseDataBaseHelper;
    private Button sumbit;

    ActivityApplicantDecideBinding activityApplicantDecideBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplicantDecideBinding = ActivityApplicantDecideBinding.inflate(getLayoutInflater());
        setContentView(activityApplicantDecideBinding.getRoot());
//        setContentView(R.layout.activity_applicant_decide);




        tableLayout = findViewById(R.id.tableApplicant);
        sumbit = findViewById(R.id.SubmitButton);
        applicantDataBaseHelper = new ApplicantDataBaseHelper(this);
        enrollmentDataBaseHelper = new EnrollmentDataBaseHelper(this);
        notificationDataBaseHelper = new NotificationDataBaseHelper(this);
        dbHelper = new AvailableCourseDataBaseHelper(this);
        listOfApplicant = findViewById(R.id.listOfApplicant);
        courseDataBaseHelper = new CourseDataBaseHelper(this);
        List<Applicant> applicants = applicantDataBaseHelper.getAllApplicants();
        items = applicantDataBaseHelper.getAllApplicationIds();
        String[] itemsArray = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemsArray[i] = String.valueOf(items.get(i));
        }

        for (Applicant a : applicants) {
            TableRow row = new TableRow(tableLayout.getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(1, 1, 1, 1);

            Applicant_id = new TextView(row.getContext());
            course_id = new TextView(row.getContext());
            email = new TextView(row.getContext());

            Applicant_id.setText(a.getApplicantId() + "");
            Applicant_id.setBackgroundColor(Color.WHITE);
            Applicant_id.setLayoutParams(layoutParams);
            Applicant_id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row.addView(Applicant_id);

            course_id.setText(CourseDataBaseHelper.getCourseName(a.getCourseId()));
            course_id.setBackgroundColor(Color.WHITE);
            course_id.setLayoutParams(layoutParams);
            course_id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row.addView(course_id);

            email.setText(a.getEmail());
            email.setBackgroundColor(Color.WHITE);
            email.setLayoutParams(layoutParams);
            email.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row.addView(email);

            tableLayout.addView(row);
        }

        listOfApplicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ApplicantDecide.this);
                builder.setTitle("Applicants :");
                builder.setCancelable(false);
                tableLayout = findViewById(R.id.student_message_table);
                builder.setSingleChoiceItems(itemsArray, selected, new DialogInterface.OnClickListener() {
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
                            Toast.makeText(ApplicantDecide.this, "This Applicant not Valid!", Toast.LENGTH_SHORT).show();
                        } else {
                            key = Integer.parseInt(itemsArray[selected]);
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

        checkboxAccept = findViewById(R.id.AcceptChcek);
        checkboxReject = findViewById(R.id.RejectCheck);

        checkboxAccept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkboxReject.setEnabled(!isChecked);
            }
        });

        checkboxReject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkboxAccept.setEnabled(!isChecked);
            }
        });
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkAccepted = (CheckBox) findViewById(R.id.AcceptChcek);
                boolean Accept = checkAccepted.isEnabled();
                CheckBox checkReject = (CheckBox) findViewById(R.id.RejectCheck);
                boolean Reject = checkReject.isEnabled();
                Applicant applicant1 = applicantDataBaseHelper.getApplicantById(key);
                Enrollment enrollment = new Enrollment(applicant1.getCourseId(), applicant1.getEmail());
                availableCourses = dbHelper.getAvailableCourseByCourse_Id(applicant1.getCourseId());
                if (Accept) {
                    if (applicantDataBaseHelper.updateApplicantStatus(key, "YES")) {
                        enrollmentDataBaseHelper.insertStudent2Course(enrollment);
                        for (Triple<AvailableCourse, String, Integer> courseInfo : availableCourses) {
                            AvailableCourse availableCourse = courseInfo.getFirst();
                            dbHelper.updateNumberOfStudent(availableCourse.getReg());
                            String message = "This Course " + CourseDataBaseHelper.getCourseName(availableCourse.getCourseId()) + "\nWill be Starting in \n" + availableCourse.getCourseStartDate();
                            String message1 = "Your Request to Registered this course " + CourseDataBaseHelper.getCourseName(availableCourse.getCourseId()) + "\n its Accepted!!";

                            // Get the course start date as a string
                            String courseStartDateString = availableCourse.getCourseStartDate();

                            // Create a date formatter
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                                        Date courseStartDate = null;
                                        try {
                                            courseStartDate = dateFormat.parse(courseStartDateString);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                            if (courseStartDate != null) {
                                // Get the current date
                                Calendar currentDate = Calendar.getInstance();
                                currentDate.setTime(new Date());

                                // Create a calendar for the course start date
                                Calendar courseStartDateCalendar = Calendar.getInstance();
                                courseStartDateCalendar.setTime(courseStartDate);

                                // Adjust the course start date calendar to ignore the time
                                courseStartDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
                                courseStartDateCalendar.set(Calendar.MINUTE, 0);
                                courseStartDateCalendar.set(Calendar.SECOND, 0);
                                courseStartDateCalendar.set(Calendar.MILLISECOND, 0);
                                // Calculate the reminder date by subtracting one day from the course start date
                                Calendar reminderDate = (Calendar) courseStartDateCalendar.clone();
                                reminderDate.add(Calendar.DAY_OF_YEAR, -1);

                                // Compare the day and month of the current date with the day and month of the reminder date
                                int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
                                int currentMonth = currentDate.get(Calendar.MONTH);
                                int reminderDay = reminderDate.get(Calendar.DAY_OF_MONTH);
                                int reminderMonth = reminderDate.get(Calendar.MONTH);

                                if (currentDay == reminderDay && currentMonth == reminderMonth) {
                                    // Call the showNotification method with the reminder date
                                    if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.VIBRATE)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        // Request the VIBRATE permission
                                        ActivityCompat.requestPermissions(ApplicantDecide.this,
                                                new String[]{Manifest.permission.VIBRATE},
                                                PERMISSION_REQUEST_CODE);
                                    } else {
                                        showNotification(v.getContext(), "Course Reminder", "Your course "+CourseDataBaseHelper.getCourseName(availableCourse.getCourseId())+ " starts tomorrow!", reminderDate.getTime());
                                    }
                                }
                            }
                            notificationDataBaseHelper.insertNotification(applicant1.getEmail(), message);
                            notificationDataBaseHelper.insertNotification(applicant1.getEmail(), message1);
                            Toast.makeText(ApplicantDecide.this, "This Course its Enrolled Successfully.", Toast.LENGTH_SHORT).show();
                        }
                        applicantDataBaseHelper.deleteApplicantById(key);
                    }
                } else if (Reject) {
                    for (Triple<AvailableCourse, String, Integer> courseInfo : availableCourses) {
                        AvailableCourse availableCourse = courseInfo.getFirst();
                        String message1 = "Your Request to Registered this course " + CourseDataBaseHelper.getCourseName(availableCourse.getCourseId()) + "\n its Rejected!!";
                        notificationDataBaseHelper.insertNotification(applicant1.getEmail(), message1);
                        applicantDataBaseHelper.updateApplicantStatus(key, "NO");
                    }
                }
            }
        });
    }
    private void showNotification(Context context, String title, String message, Date reminderDate) {
        // Check if the VIBRATE permission is granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Handle the case where the permission is not granted
            // You can request the permission from the user or show an error message
            // Here, we are simply returning without showing the notification
            return;
        }

        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "course_reminder_channel";
            CharSequence channelName = "Course Reminder Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

            // Register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "course_reminder_channel")
                .setSmallIcon(R.drawable.admin)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true); // Auto cancel the notification when clicked

        // Create a notification manager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Show the notification
        notificationManager.notify(0, builder.build());
    }



}