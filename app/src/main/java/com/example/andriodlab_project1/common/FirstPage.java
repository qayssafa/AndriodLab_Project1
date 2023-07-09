package com.example.andriodlab_project1.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course_for_registration.AvailableCourseDataBaseHelper;

import java.util.List;

import kotlin.Triple;

public class FirstPage extends AppCompatActivity {


    TextView id1,id2, id3, id4, id5;
    TextView date1, date2, date3, date4, date5;
    TextView title1, title2, title3, title4, title5;


    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        id1 = findViewById(R.id.courseoneid);
        id2 = findViewById(R.id.coursetwoid);
        id3 = findViewById(R.id.coursid3);
        id4 = findViewById(R.id.coursid4);
        id5 = findViewById(R.id.coursid5);
        title1 = findViewById(R.id.courseonetital);
        title2 = findViewById(R.id.tital2);
        title3 = findViewById(R.id.tital3);
        title4 = findViewById(R.id.tital4);
        title5 = findViewById(R.id.tital5);
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        date4 = findViewById(R.id.date4);
        date5 = findViewById(R.id.date5);
        btnLogin = findViewById(R.id.nextpage);
        AvailableCourseDataBaseHelper availableCourseDataBaseHelper=new AvailableCourseDataBaseHelper(this);
        List<Triple<String, String, String>> courses = availableCourseDataBaseHelper.getRecentlyAddedCourses();

        int numCourses = courses != null ? courses.size() : 0;

        for (int i = 0; i < 5; i++) {
            TextView idTextView = null;
            TextView titleTextView = null;
            TextView dateTextView = null;

            // Find the TextViews dynamically based on the loop index
            switch (i) {
                case 0:
                    idTextView = id1;
                    titleTextView = title1;
                    dateTextView = date1;
                    break;
                case 1:
                    idTextView = id2;
                    titleTextView = title2;
                    dateTextView = date2;
                    break;
                case 2:
                    idTextView = id3;
                    titleTextView = title3;
                    dateTextView = date3;
                    break;
                case 3:
                    idTextView = id4;
                    titleTextView = title4;
                    dateTextView = date4;
                    break;
                case 4:
                    idTextView = id5;
                    titleTextView = title5;
                    dateTextView = date5;
                    break;
            }

            if (i < numCourses) {
                Triple<String, String, String> courseInfo = courses.get(i);
                idTextView.setText(courseInfo.getFirst());
                titleTextView.setText(courseInfo.getSecond());
                dateTextView.setText(courseInfo.getThird());
            } else {
                idTextView.setText("N/A");
                titleTextView.setText("N/A");
                dateTextView.setText("N/A");
            }
        }


        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(FirstPage.this, MainActivity.class));
        });
    }
}