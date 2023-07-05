package com.example.andriodlab_project1.signup;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.andriodlab_project1.R;

public class instructorFragment extends Fragment {

    EditText phoneInstructor;
    EditText addressInstructor;
    EditText specialization;
    EditText courses;
    private CheckBox checkBoxBSc;
    private CheckBox checkBoxMSc;
    private CheckBox checkBoxPhD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        phoneInstructor = getActivity().findViewById(R.id.editPhone);
        addressInstructor = getActivity().findViewById(R.id.editAddress);
        specialization = getActivity().findViewById(R.id.editSpecialization);
        courses = getActivity().findViewById(R.id.editListOfCourses);
        checkBoxBSc = getActivity().findViewById(R.id.checkBoxBSc);
        checkBoxMSc = getActivity().findViewById(R.id.checkBoxMSc);
        checkBoxPhD = getActivity().findViewById(R.id.checkBoxPhD);
    }

    public void setEmpty() {
        phoneInstructor.setText("");
        addressInstructor.setText("");
        specialization.setText("");
        courses.setText("");
    }

    public String getPhoneInstructorValue() {

        if (phoneInstructor != null) {
            return phoneInstructor.getText().toString();
        }
        return null;
    }

    public String getAddressInstructorValue() {

        if (addressInstructor != null) {
            return addressInstructor.getText().toString();
        }
        return null;
    }

    public String getSpecializationValue() {

        if (specialization != null) {
            return specialization.getText().toString();
        }
        return null;
    }

    public String getListOfCoursesValue() {

        if (courses != null) {
            return courses.getText().toString();
        }
        return null;
    }

    public boolean isCheckedBSc() {
        return checkBoxBSc.isChecked();
    }

    public boolean isCheckedMSc() {
        return checkBoxMSc.isChecked();
    }

    public boolean isCheckedPhD() {
        return checkBoxPhD.isChecked();
    }
}