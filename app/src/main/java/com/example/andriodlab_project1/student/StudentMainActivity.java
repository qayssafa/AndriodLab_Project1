package com.example.andriodlab_project1.student;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.StudentDrawerBaseActivity;
import com.example.andriodlab_project1.common.User;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;
import com.example.andriodlab_project1.enrollment.EnrollmentDataBaseHelper;
import com.example.andriodlab_project1.notification.Notification;
import com.example.andriodlab_project1.notification.NotificationDataBaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.Triple;

public class StudentMainActivity extends StudentDrawerBaseActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private AvailableCourseDataBaseHelper dbHelper;
    private CourseDataBaseHelper dataBaseHelper;
    private List<Triple<AvailableCourse, String, Integer>> availableCourse;
    int notificationId = 0;
    StudentDataBaseHelper studentDataBaseHelper;
    EnrollmentDataBaseHelper enrollmentDataBaseHelper;
    com.example.andriodlab_project1.databinding.ActivityStudentMainBinding activityStudentMainBinding;
    private NotificationDataBaseHelper notificationDataBaseHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStudentMainBinding = com.example.andriodlab_project1.databinding.ActivityStudentMainBinding.inflate(getLayoutInflater());
        setContentView(activityStudentMainBinding.getRoot());

        notificationDataBaseHelper=new NotificationDataBaseHelper(this);
        dataBaseHelper = new CourseDataBaseHelper(this);
        TextView studentName = (TextView) findViewById(R.id.StudentName);
        studentDataBaseHelper = new StudentDataBaseHelper(this);
        dbHelper = new AvailableCourseDataBaseHelper(this);
        enrollmentDataBaseHelper = new EnrollmentDataBaseHelper(this);
        studentName.setText(studentDataBaseHelper.getStudentByEmail(MainActivity.studentEmail).getFirstName() + " " + studentDataBaseHelper.getStudentByEmail(MainActivity.studentEmail).getLastName());

        sendCourseReminderNotification();

    }
    private void sendCourseReminderNotification() {
        List<Integer> courses = enrollmentDataBaseHelper.getCoursesByStudentEmail(MainActivity.studentEmail);
        for (Integer courseId : courses) {
            List<Triple<AvailableCourse, String, Integer>> availableCourses = dbHelper.getAvailableCourseByCourse_Id(courseId);
            for (Triple<AvailableCourse, String, Integer> availableCourse : availableCourses) {
                AvailableCourse course = availableCourse.getFirst();
                String message = "Your Course " + dataBaseHelper.getCourseName(course.getCourseId()) + " Starts Tomorrow!";
                if (!isMessageSent(message)) {
                    if (shouldSendNotification(course.getCourseStartDate())) {
                        sendCourseStartNotification(message, notificationId);
                        notificationId++;
                        markMessageAsSent(message);
                    }
                }
            }
        }
            List<Notification> notifications=notificationDataBaseHelper.getNotificationsForStudent(MainActivity.studentEmail);
            for (Notification notification : notifications) {
                sendGeneralNotification(notification.getMessage());
            }

        }

    private void sendCourseStartNotification(String message, int notificationId) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(StudentMainActivity.this, new String[]{Manifest.permission.VIBRATE}, PERMISSION_REQUEST_CODE);
        } else {
            showNotification(this, "Course Reminder", message, notificationId);
        }
    }
    private void sendGeneralNotification(String message) {
        if (!isMessageSent(message)) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(StudentMainActivity.this, new String[]{Manifest.permission.VIBRATE}, PERMISSION_REQUEST_CODE);
            } else {
                showNotification(this, "Notification", message, notificationId);
                notificationId++;
            }
            markMessageAsSent(message);
        }
    }

    private boolean isMessageSent(String message) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(message, false);
    }

    private void markMessageAsSent(String message) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(message, true);
        editor.apply();
    }

    private boolean shouldSendNotification(String courseStartDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date courseStartDate;

        try {
            courseStartDate = dateFormat.parse(courseStartDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());

        Calendar courseStartDateCalendar = Calendar.getInstance();
        courseStartDateCalendar.setTime(courseStartDate);
        courseStartDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        courseStartDateCalendar.set(Calendar.MINUTE, 0);
        courseStartDateCalendar.set(Calendar.SECOND, 0);
        courseStartDateCalendar.set(Calendar.MILLISECOND, 0);

        Calendar reminderDate = (Calendar) courseStartDateCalendar.clone();
        reminderDate.add(Calendar.DAY_OF_YEAR, -1);

        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int reminderDay = reminderDate.get(Calendar.DAY_OF_MONTH);
        int reminderMonth = reminderDate.get(Calendar.MONTH);

        return currentDay == reminderDay && currentMonth == reminderMonth;
    }

    private void showNotification(Context context, String title, String message, int notificationId) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "course_reminder_channel";
            CharSequence channelName = "Course Reminder Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "course_reminder_channel")
                .setSmallIcon(R.drawable.reminder)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }
}
