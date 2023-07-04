package com.example.andriodlab_project1.admin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.andriodlab_project1.R;
import com.example.andriodlab_project1.student.Student;

public class ViewStudentProfileFragment extends Fragment {

    private TextView firstName;
    private TextView lastName;
    private TextView numberMobile;
    private TextView address;
    private TextView emailAd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_student_profile, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    };

    public void changeDataForStudent(String lEmail,String lFirstName,String lLastName,String lNumber,String lAddress){
        firstName = (TextView) getActivity().findViewById(R.id.StuFirstName);
        lastName = (TextView) getActivity().findViewById(R.id.LastName);
        numberMobile = (TextView) getActivity().findViewById(R.id.mobile);
        address = (TextView) getActivity().findViewById(R.id.addressS);
        emailAd = (TextView) getActivity().findViewById(R.id.emailAd);

        firstName.setText(lFirstName);
        numberMobile.setText(lLastName);
        lastName.setText(lNumber);
        address.setText(lAddress);
        emailAd.setText(lEmail);
    }

    interface communicatorStudent {
        public void respond(Student student);
    }
}