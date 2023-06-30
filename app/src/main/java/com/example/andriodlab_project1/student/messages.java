package com.example.andriodlab_project1.student;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;
import com.example.andriodlab_project1.course_for_registration.AvailableCourse;
import com.example.andriodlab_project1.notification.Notification;
import com.example.andriodlab_project1.notification.NotificationDataBaseHelper;
import com.example.andriodlab_project1.signup.SignUPMainActivity;

import java.util.List;

import kotlin.Triple;

public class messages extends AppCompatActivity {
    private TableLayout tableLayout;
    private TextView message;
    private TextView time;
    private NotificationDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        dbHelper = new NotificationDataBaseHelper(this);
        List<Notification> notifications= dbHelper.getNotificationsForStudent(MainActivity.studentEmail);
        tableLayout = findViewById(R.id.student_message_table);
        for (Notification notification : notifications) {

            TableRow row = new TableRow(tableLayout.getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(1, 1, 1, 1);
            message= new TextView(row.getContext());
            time = new TextView(row.getContext());

            message.setText(notification.getMessage());
            message.setBackgroundColor(Color.WHITE);
            message.setLayoutParams(layoutParams);
            message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row.addView(message);

            time.setText(notification.getTimestamp());
            time.setBackgroundColor(Color.WHITE);
            time.setLayoutParams(layoutParams);
            time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);;
            row.addView(time);

            tableLayout.addView(row);
        }
    }
    }
