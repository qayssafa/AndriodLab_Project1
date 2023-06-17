package com.example.andriodlab_project1;

public class Student extends User {
    private String mobileNumber;
    private String address;

    public Student(String email, String firstName, String lastName, String password, String mobileNumber, String address) {
        super(email, firstName, lastName, password);
        this.mobileNumber = mobileNumber;
        this.address = address;
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
}

