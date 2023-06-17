package com.example.andriodlab_project1.instructor;

import com.example.andriodlab_project1.common.User;

import java.util.List;

public class Instructor extends User {
    private String mobileNumber;
    private String address;
    private String specialization;
    private String degree;
    private List<String> coursesTaught;

    public Instructor(String email, String firstName, String lastName, String password, String mobileNumber,
                      String address, String specialization, String degree, List<String> coursesTaught) {
        super(email, firstName, lastName, password);
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.specialization = specialization;
        this.degree = degree;
        this.coursesTaught = coursesTaught;
    }

    public Instructor() {

    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public List<String> getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(List<String> coursesTaught) {
        this.coursesTaught = coursesTaught;
    }
}

