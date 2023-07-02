package com.example.andriodlab_project1.signup;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.andriodlab_project1.R;


public class StudentFragment extends Fragment {
     EditText phoneStudent ;
     EditText addressStudent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        phoneStudent = (EditText) getActivity().findViewById(R.id.editPhoneNumberStudent);
        addressStudent = (EditText) getActivity().findViewById(R.id.editAddressStudent);
    }
    public String getPhoneStudentValue() {

        if (phoneStudent != null) {
            return phoneStudent.getText().toString();
        }
        return null;
    }
    public String getAddressStudentValue() {

        if (addressStudent != null) {
            return addressStudent.getText().toString();
        }
        return null;
    }
}