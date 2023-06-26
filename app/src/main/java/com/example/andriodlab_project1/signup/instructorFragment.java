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

    private EditText phoneInstructor;
    private EditText addressInstructor;
    private EditText specialization ;
    private EditText courses;
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
        phoneInstructor = (EditText) getActivity().findViewById(R.id.editPhone);
        addressInstructor = (EditText) getActivity().findViewById(R.id.editAddress);
        specialization = (EditText) getActivity().findViewById(R.id.editSpecialization);
        courses = (EditText) getActivity().findViewById(R.id.editListOfCourses);
        checkBoxBSc=(CheckBox) getActivity().findViewById(R.id.checkBoxBSc);
        checkBoxMSc=(CheckBox) getActivity().findViewById(R.id.checkBoxMSc);
        checkBoxPhD=(CheckBox) getActivity().findViewById(R.id.checkBoxPhD);
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
    public boolean isCheckedBSc(){
        return checkBoxBSc.isChecked();
    }
    public boolean isCheckedMSc(){
        return checkBoxMSc.isChecked();
    }
    public boolean isCheckedPhD(){
        return checkBoxPhD.isChecked();
    }
}