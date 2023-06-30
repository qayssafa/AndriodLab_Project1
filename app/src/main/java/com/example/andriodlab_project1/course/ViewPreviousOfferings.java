package com.example.andriodlab_project1.course;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.andriodlab_project1.R;

public class ViewPreviousOfferings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_previous_offerings);

        TableLayout tableLayout = findViewById(R.id.table);
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(1, 1, 1, 1);

        TextView courseNumber = new TextView(this);
        courseNumber.setText(String.valueOf(12345));
        courseNumber.setBackgroundColor(Color.WHITE);
        courseNumber.setLayoutParams(layoutParams);
        courseNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(courseNumber);

        TextView courseTitle = new TextView(this);
        courseTitle.setText("hello");
        courseTitle.setBackgroundColor(Color.WHITE);
        courseTitle.setLayoutParams(layoutParams);
        courseTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(courseTitle);

        TextView date = new TextView(this);
        date.setText("hi");
        date.setBackgroundColor(Color.WHITE);
        date.setLayoutParams(layoutParams);
        date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(date);

        TextView time = new TextView(this);
        time.setText("uuu");
        time.setBackgroundColor(Color.WHITE);
        time.setLayoutParams(layoutParams);
        time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);;
        row.addView(time);

        TextView numberOfStudents = new TextView(this);
        numberOfStudents.setText("son");
        numberOfStudents.setBackgroundColor(Color.WHITE);
        numberOfStudents.setLayoutParams(layoutParams);
        numberOfStudents.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(numberOfStudents);

        TextView venue = new TextView(this);
        venue.setText("deek");
        venue.setBackgroundColor(Color.WHITE);
        venue.setLayoutParams(layoutParams);
        venue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(venue);

        TextView instructorName = new TextView(this);
        instructorName.setText("legend");
        instructorName.setBackgroundColor(Color.WHITE);
        instructorName.setLayoutParams(layoutParams);
        instructorName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(instructorName);

        // Add the TableRow to the TableLayout
        tableLayout.addView(row);

    }
}