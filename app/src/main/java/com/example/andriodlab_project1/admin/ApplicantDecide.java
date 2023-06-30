package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.andriodlab_project1.R;

public class ApplicantDecide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_decide);

        CheckBox checkboxAccept = findViewById(R.id.AcceptChcek);
        CheckBox checkboxReject = findViewById(R.id.RejectCheck);

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

        CheckBox checkAccepted = (CheckBox)findViewById(R.id.AcceptChcek);
        boolean Accept = checkAccepted.isEnabled();
        CheckBox checkReject = (CheckBox)findViewById(R.id.AcceptChcek);
        boolean Reject = checkAccepted.isEnabled();
    }
}