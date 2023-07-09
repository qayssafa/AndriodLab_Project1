package com.example.andriodlab_project1.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;

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

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(FirstPage.this, MainActivity.class));
        });
    }
}