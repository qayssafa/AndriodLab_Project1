package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.graphics.Color;
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

import java.util.List;

import kotlin.Triple;

public class ApplicantDecideActivity extends DrawerBaseActivity {

    private CheckBox checkboxAccept;
    private CheckBox checkboxReject;
    private TableLayout tableLayout;
    private List<Triple<AvailableCourse, String, Integer>> availableCourses;
    private int key;
    private int selected = 0;
    private ApplicantDataBaseHelper applicantDataBaseHelper;
    private EnrollmentDataBaseHelper enrollmentDataBaseHelper;
    private AvailableCourseDataBaseHelper dbHelper;
    private NotificationDataBaseHelper notificationDataBaseHelper;
    private CourseDataBaseHelper courseDataBaseHelper;
    ActivityApplicantDecideBinding activityApplicantDecideBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplicantDecideBinding = ActivityApplicantDecideBinding.inflate(getLayoutInflater());
        setContentView(activityApplicantDecideBinding.getRoot());
        tableLayout = findViewById(R.id.tableApplicant);
        Button submit = findViewById(R.id.SubmitButton);
        applicantDataBaseHelper = new ApplicantDataBaseHelper(this);
        enrollmentDataBaseHelper = new EnrollmentDataBaseHelper(this);
        notificationDataBaseHelper = new NotificationDataBaseHelper(this);
        courseDataBaseHelper = new CourseDataBaseHelper(this);
        dbHelper = new AvailableCourseDataBaseHelper(this);
        TextView listOfApplicant = findViewById(R.id.listOfApplicant);
        List<Applicant> applicants = applicantDataBaseHelper.getAllApplicants();
        List<Integer> items = applicantDataBaseHelper.getAllApplicationIds();
        String[] itemsArray = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemsArray[i] = String.valueOf(items.get(i));
        }


        for (Applicant a : applicants) {
            if (!(applicantDataBaseHelper.getApplicantStatus(a.getApplicantId()).equals("YES") || applicantDataBaseHelper.getApplicantStatus(a.getApplicantId()).equals("NO"))) {
                TableRow row = new TableRow(tableLayout.getContext());
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(1, 1, 1, 1);

                TextView applicant_id = new TextView(row.getContext());
                TextView course_id = new TextView(row.getContext());
                TextView email = new TextView(row.getContext());

                applicant_id.setText(String.valueOf(a.getApplicantId()));
                applicant_id.setBackgroundColor(Color.WHITE);
                applicant_id.setLayoutParams(layoutParams);
                applicant_id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(applicant_id);

                course_id.setText(courseDataBaseHelper.getCourseName(a.getCourseId()));
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
        }
        if (!(itemsArray.length == 0)) {
            listOfApplicant.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ApplicantDecideActivity.this);
                builder.setTitle("Applicants :");
                builder.setCancelable(false);
                tableLayout = findViewById(R.id.student_message_table);
                builder.setSingleChoiceItems(itemsArray, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = which;  // Update the selected continent index
                    }
                });
                builder.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    if (selected < 0) {
                        Toast.makeText(ApplicantDecideActivity.this, "This Applicant not Valid!", Toast.LENGTH_SHORT).show();
                    } else {
                        key = Integer.parseInt(itemsArray[selected]);
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                builder.show();
            });
        } else {
            Toast.makeText(ApplicantDecideActivity.this, "No Applicant Request Are found.", Toast.LENGTH_SHORT).show();

        }


        checkboxAccept = findViewById(R.id.AcceptChcek);
        checkboxReject = findViewById(R.id.RejectCheck);

        checkboxAccept.setOnCheckedChangeListener((buttonView, isChecked) -> checkboxReject.setEnabled(!isChecked));

        checkboxReject.setOnCheckedChangeListener((buttonView, isChecked) -> checkboxAccept.setEnabled(!isChecked));
        submit.setOnClickListener(v -> {
            CheckBox checkAccepted = findViewById(R.id.AcceptChcek);
            boolean Accept = checkAccepted.isEnabled();
            CheckBox checkReject = findViewById(R.id.RejectCheck);
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
                        String message = "Your Course " + courseDataBaseHelper.getCourseName(availableCourse.getCourseId()) + " Starts Tomorrow!";
                        String message1 = "Your Request to Registered this course " + courseDataBaseHelper.getCourseName(availableCourse.getCourseId()) + " its Accepted!!";
                        notificationDataBaseHelper.insertNotification(applicant1.getEmail(), message);
                        notificationDataBaseHelper.insertNotification(applicant1.getEmail(), message1);
                        Toast.makeText(ApplicantDecideActivity.this, "This Course its Enrolled Successfully.", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (Reject) {
                if (applicantDataBaseHelper.updateApplicantStatus(key, "NO")) {
                    for (Triple<AvailableCourse, String, Integer> courseInfo : availableCourses) {
                        AvailableCourse availableCourse = courseInfo.getFirst();
                        String message1 = "Your Request to Registered this course " + courseDataBaseHelper.getCourseName(availableCourse.getCourseId()) + " its Rejected!!";
                        notificationDataBaseHelper.insertNotification(applicant1.getEmail(), message1);
                        applicantDataBaseHelper.updateApplicantStatus(key, "NO");
                        Toast.makeText(ApplicantDecideActivity.this, "This Applicant its Rejected Successfully.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}