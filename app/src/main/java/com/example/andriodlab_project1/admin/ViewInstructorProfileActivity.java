package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.course.EditOrDeleteAnExistingCourseActivity;
import com.example.andriodlab_project1.databinding.ActivityViewInstructorProfileBinding;
import com.example.andriodlab_project1.instructor.Instructor;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.student.Student;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewInstructorProfileActivity extends DrawerBaseActivity {

    TextView instructorFName;
    TextView instructorLName;
    EditText instructorEmail;
    TextView instructorPhone;
    TextView instructorAddress;
    TextView instructorSpecialization;
    TextView instructorDegree;
    ImageButton searchInstructor;
    private InstructorDataBaseHelper instructorDataBaseHelper;
    private StudentDataBaseHelper studentDataBaseHelper;

    private List<Map.Entry<String, String>> continents;

    private int selected;
    public static int id;
    CharSequence[] items;


    private Instructor instructor;

    ActivityViewInstructorProfileBinding activityViewInstructorProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewInstructorProfileBinding = ActivityViewInstructorProfileBinding.inflate(getLayoutInflater());
        setContentView(activityViewInstructorProfileBinding.getRoot());
        //setContentView(R.layout.activity_view_instructor_profile);
        instructorFName = findViewById(R.id.InsFirstName);
        instructorLName = findViewById(R.id.InsLastName);
        instructorEmail = findViewById(R.id.enterEmail);
        instructorPhone = findViewById(R.id.InsMobileNumber);
        instructorAddress = findViewById(R.id.InsAddress);
        instructorSpecialization = findViewById(R.id.InsSpecialization);
        instructorDegree = findViewById(R.id.InsDegree);
        searchInstructor = findViewById(R.id.searchInstructor);
        TextView listOfinc = findViewById(R.id.list);

        instructorDataBaseHelper = new InstructorDataBaseHelper(this);
        studentDataBaseHelper = new StudentDataBaseHelper(this);
        continents = instructorDataBaseHelper.getAllInstructorsEmailAndName();

            if (!(continents.isEmpty())) {
                listOfinc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewInstructorProfileActivity.this);
                        builder.setTitle("Instructor:");
                        builder.setCancelable(false);

                        items = convertListToCharSequenceArray(continents);
                        builder.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
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
                                    Toast.makeText(ViewInstructorProfileActivity.this, "This Course not Valid!", Toast.LENGTH_SHORT).show();
                                } else {
                                    String selectedInstructorEmail = continents.get(selected).getKey();
                                    instructor = instructorDataBaseHelper.getInstructorByEmail(selectedInstructorEmail);
                                    instructorEmail.setText(String.valueOf(instructor.getEmail()));
                                    instructorFName.setText("");
                                    instructorLName.setText("");
                                    instructorPhone.setText("");
                                    instructorAddress.setText("");
                                    instructorSpecialization.setText("");
                                    instructorDegree.setText("");



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
        }

        searchInstructor.setOnClickListener(v -> {
            if (!(instructorEmail.getText().toString().isEmpty()) && studentDataBaseHelper.isRegistered(instructorEmail.getText().toString())) {
                Student student1 = studentDataBaseHelper.getStudentByEmail(instructorEmail.getText().toString());
            } else if (!(instructorEmail.getText().toString().isEmpty()) && instructorDataBaseHelper.isRegistered(instructorEmail.getText().toString())) {

                Instructor instructor1 = instructorDataBaseHelper.getInstructorByEmail(instructorEmail.getText().toString());

                instructorLName.setText(String.valueOf(instructor1.getLastName()));
                instructorFName.setText(String.valueOf(instructor1.getFirstName()));
                instructorPhone.setText(String.valueOf(instructor1.getMobileNumber()));
                instructorAddress.setText(String.valueOf(instructor1.getAddress()));
                instructorSpecialization.setText(String.valueOf(instructor1.getSpecialization()));
                instructorDegree.setText(String.valueOf(instructor1.getDegree()));

            } else {
                Toast.makeText(ViewInstructorProfileActivity.this, "This Email its not Valid.", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public static String convertArrayListToString(ArrayList<String> arrayList) {
        return String.join(",", arrayList);
    }
    public static CharSequence[] convertListToCharSequenceArray(List<Map.Entry<String, String>> list) {
        CharSequence[] array = new CharSequence[list.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : list) {
            array[i++] = entry.getValue();
        }
        return array;
    }

}