package com.example.andriodlab_project1.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriodlab_project1.DrawerBaseActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.databinding.ActivityViewStudentProfileBinding;
import com.example.andriodlab_project1.instructor.Instructor;
import com.example.andriodlab_project1.instructor.InstructorDataBaseHelper;
import com.example.andriodlab_project1.student.Student;
import com.example.andriodlab_project1.student.StudentDataBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewStudentProfileActivity extends DrawerBaseActivity {

    TextView studentFName;
    TextView studentLName;
    EditText studentEmail;
    TextView studentPhone;
    TextView studentAddress;

    ImageButton searchInstructor;
    private InstructorDataBaseHelper instructorDataBaseHelper;
    private StudentDataBaseHelper studentDataBaseHelper;

    private List<Map.Entry<String, String>> continents;

    private int selected;
    public static int id;
    CharSequence[] items;


    private Student student;
    ActivityViewStudentProfileBinding activityViewStudentProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewStudentProfileBinding = ActivityViewStudentProfileBinding.inflate(getLayoutInflater());
        setContentView(activityViewStudentProfileBinding.getRoot());
        //setContentView(R.layout.activity_view_student_profile);

        studentFName = findViewById(R.id.InsFirstName);
        studentLName = findViewById(R.id.InsLastName);
        studentEmail = findViewById(R.id.enterEmail);
        studentPhone = findViewById(R.id.InsMobileNumber);
        studentAddress = findViewById(R.id.InsAddress);
        searchInstructor = findViewById(R.id.searchInstructor);
        TextView listOfStudent = findViewById(R.id.list);

        instructorDataBaseHelper = new InstructorDataBaseHelper(this);
        studentDataBaseHelper  = new StudentDataBaseHelper(this);
        continents = studentDataBaseHelper.getAllStudentEmailAndName();
        if (!(continents.isEmpty())) {
            listOfStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewStudentProfileActivity.this);
                    builder.setTitle("Student:");
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
                                Toast.makeText(ViewStudentProfileActivity.this, "Not Valid!", Toast.LENGTH_SHORT).show();
                            } else {
                                String selectedInstructorEmail = continents.get(selected).getKey();
                                student = studentDataBaseHelper.getStudentByEmail(selectedInstructorEmail);
                                studentEmail.setText(String.valueOf(student.getEmail()));
                                studentFName.setText("");
                                studentLName.setText("");
                                studentPhone.setText("");
                                studentAddress.setText("");


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
            if (!(studentEmail.getText().toString().isEmpty()) && studentDataBaseHelper.isRegistered(studentEmail.getText().toString())) {
                Student student1 = studentDataBaseHelper.getStudentByEmail(studentEmail.getText().toString());
                studentLName.setText(String.valueOf(student1.getLastName()));
                studentFName.setText(String.valueOf(student1.getFirstName()));
                studentPhone.setText(String.valueOf(student1.getMobileNumber()));
                studentAddress.setText(String.valueOf(student1.getAddress()));
            } else {
                Toast.makeText(ViewStudentProfileActivity.this, "This Email its not Valid.", Toast.LENGTH_SHORT).show();

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