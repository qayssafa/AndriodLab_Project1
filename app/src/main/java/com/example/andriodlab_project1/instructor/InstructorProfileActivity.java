package com.example.andriodlab_project1.instructor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.example.andriodlab_project1.InstructorDrawerBaswActivity;
import com.example.andriodlab_project1.MainActivity;
import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.databinding.ActivityInstructorProfileBinding;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.andriodlab_project1.R;

import java.util.regex.Pattern;

public class InstructorProfileActivity extends InstructorDrawerBaswActivity {

    ActivityInstructorProfileBinding activityInstructorProfileBinding;

    EditText firstName, lastName, mobileNumber, address, specialization, degree, password,insEmail;

    TextView instructorName;
    Button editProfile;

    ImageView changeimages;

    Button changeImagesButton;

    private InstructorDataBaseHelper instructorDataBaseHelper;

    public static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInstructorProfileBinding = ActivityInstructorProfileBinding.inflate(getLayoutInflater());
        setContentView(activityInstructorProfileBinding.getRoot());
        //setContentView(R.layout.activity_instructor_profile);

        firstName = findViewById(R.id.InsFirstName);
        lastName = findViewById(R.id.InsLastName);
        mobileNumber = findViewById(R.id.InsMobileNumber);
        address = findViewById(R.id.InsAddress);
        specialization = findViewById(R.id.InsSpecialization);
        degree = findViewById(R.id.InsDegree);
        password = findViewById(R.id.inspassword);
        editProfile = findViewById(R.id.buttonEditProfile);
        instructorName = findViewById(R.id.inccName);
        insEmail = findViewById(R.id.insEmail);
        changeimages = findViewById(R.id.imageView18);
        changeImagesButton = findViewById(R.id.changeImage);


        instructorDataBaseHelper = new InstructorDataBaseHelper(this);
        instructorName.setText(instructorDataBaseHelper.getInstructorByEmail(MainActivity.instructorEmail).getFirstName() + " " + instructorDataBaseHelper.getInstructorByEmail(MainActivity.instructorEmail).getLastName());
        Instructor instructor = instructorDataBaseHelper.getInstructorByEmail(MainActivity.instructorEmail);
        setInstructorData(instructor);
        Bitmap Photo1 = instructorDataBaseHelper.getImage(insEmail.getText().toString());
        if(Photo1 != null) {
            //courseImageView = findViewById(R.id.imageView4);
            changeimages.setImageBitmap(Photo1);
        }

        changeImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNameText = firstName.getText().toString();
                String lastNameText = lastName.getText().toString();
                String mobileNumberText = mobileNumber.getText().toString();
                String addressText = address.getText().toString();
                String specializationText = specialization.getText().toString();
                String degreeText = degree.getText().toString();
                String passwordText = password.getText().toString();
                String insEmailText = insEmail.getText().toString();
                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                Pattern emailPattern = Pattern.compile(emailRegex);

                if (firstNameText.isEmpty() || firstNameText.isBlank() || firstNameText.length() < 3 || firstNameText.length() > 20) {
                    Toast.makeText(InstructorProfileActivity.this, "First name must be between 3 and 20 characters!", Toast.LENGTH_SHORT).show();
                }
                else if (lastNameText.isEmpty() || lastNameText.isBlank() || lastNameText.length() < 3 || lastNameText.length() > 20) {
                    showToastMessage("Last name must be between 3 and 20 characters!");
                }else  if (passwordText.isEmpty() || passwordText.isBlank() || passwordText.length() < 8 || passwordText.length() > 15 ||
                        !passwordText.matches(".*\\d.*") || !passwordText.matches(".*[a-z].*") || !passwordText.matches(".*[A-Z].*")) {
                    showToastMessage("Password must be between 8 and 15 characters and contain at least one number, one lowercase letter, and one uppercase letter!");
                } else if (mobileNumberText.isEmpty() || mobileNumberText.isBlank() || mobileNumberText.length() != 10) {
                    showToastMessage("Mobile number must be 10 digits!");
                } else if (addressText.isEmpty() || addressText.isBlank()  ) {
                    showToastMessage("Address field is empty ");
                } else if (specializationText.isEmpty() || specializationText.isBlank() ) {
                    showToastMessage("Specialization field is empty");
                } else if (degreeText.isEmpty() || degreeText.isBlank() ) {
                    showToastMessage("Degree field is empty");
                }
                else if (insEmailText.isEmpty() || insEmailText.isBlank() || !emailPattern.matcher(insEmailText).matches()) {
                    showToastMessage("This Email not Valid!");
                }
                else {
                    Instructor updatedInstructor = new Instructor();
                    updatedInstructor.setFirstName(firstNameText);
                    updatedInstructor.setLastName(lastNameText);
                    updatedInstructor.setMobileNumber(mobileNumberText);
                    updatedInstructor.setAddress(addressText);
                    updatedInstructor.setSpecialization(specializationText);
                    updatedInstructor.setDegree(degreeText);
                    updatedInstructor.setPassword(passwordText);
                    updatedInstructor.setEmail(insEmailText);
                    if(imageToStore != null){
                        changeimages.setImageBitmap(imageToStore);
                    }
                    if(imageToStore != null)
                        updatedInstructor.setPhoto(imageToStore);
                    else
                        updatedInstructor.setPhoto(Photo1);

                    instructorDataBaseHelper.updateInstructor(updatedInstructor);

                    Toast.makeText(InstructorProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                    instructorName.setText(instructorDataBaseHelper.getInstructorByEmail(MainActivity.instructorEmail).getFirstName() + " " + instructorDataBaseHelper.getInstructorByEmail(MainActivity.instructorEmail).getLastName());
                }

            }
        });
    }

    private void showToastMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        TextView toastText = toastView.findViewById(android.R.id.message);
        toastText.setTextColor(Color.RED); // Set text color here
        toastView.setBackgroundColor(Color.TRANSPARENT); // Set background color here
        toast.show();
    }

    private void setInstructorData(Instructor instructor) {
        insEmail.setText(instructor.getEmail());
        insEmail.setSelection(insEmail.getText().length());
        insEmail.clearFocus();
        firstName.setText(instructor.getFirstName());
        firstName.setSelection(firstName.getText().length());
        firstName.clearFocus();
        lastName.setText(instructor.getLastName());
        lastName.setSelection(lastName.getText().length());
        lastName.clearFocus();
        mobileNumber.setText(instructor.getMobileNumber());
        mobileNumber.setSelection(mobileNumber.getText().length());
        mobileNumber.clearFocus();
        address.setText(instructor.getAddress());
        address.setSelection(address.getText().length());
        address.clearFocus();
        specialization.setText(instructor.getSpecialization());
        specialization.setSelection(specialization.getText().length());
        specialization.clearFocus();
        degree.setText(instructor.getDegree());
        degree.setSelection(degree.getText().length());
        degree.clearFocus();
        password.setText(instructor.getPassword());
        password.setSelection(password.getText().length());
        password.clearFocus();

        //changeimages.
    }

    public void chooseImage() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}