package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.databinding.ActivityApplicantDecideBinding;

public class ApplicantDecide extends DrawerBaseActivity {

    private CheckBox checkboxAccept;
    private CheckBox checkboxReject;

    ActivityApplicantDecideBinding activityApplicantDecideBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplicantDecideBinding = ActivityApplicantDecideBinding.inflate(getLayoutInflater());
        setContentView(activityApplicantDecideBinding.getRoot());
        //setContentView(R.layout.activity_applicant_decide);

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

        CheckBox checkAccepted = (CheckBox)findViewById(R.id.AcceptChcek);
        boolean Accept = checkAccepted.isEnabled();
        CheckBox checkReject = (CheckBox)findViewById(R.id.AcceptChcek);
        boolean Reject = checkAccepted.isEnabled();
    }
}