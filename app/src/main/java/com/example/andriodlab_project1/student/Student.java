package com.example.andriodlab_project1.student;

import android.graphics.Bitmap;

import com.example.andriodlab_project1.common.User;

public class Student extends User {
    private String mobileNumber;
    private String address;
    private Bitmap photo;

    public Student(String email, String firstName, String lastName, String password, String mobileNumber, String address,Bitmap photo) {
        super(email, firstName, lastName, password);
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.photo = photo;
    }

    public Student() {

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

    public Bitmap getPhoto() {return photo;}

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoto(Bitmap photo) {this.photo = photo;}
}

