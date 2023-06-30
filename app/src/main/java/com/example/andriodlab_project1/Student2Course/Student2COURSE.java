package com.example.andriodlab_project1.Student2Course;

public class Student2COURSE {

    private int CourseID;
    private String StudentEmail;

    public Student2COURSE(int courseID, String studentEmail) {
        CourseID = courseID;
        StudentEmail = studentEmail;
    }

    public Student2COURSE() {
    }

    public int getCourseID() {
        return CourseID;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }
}
