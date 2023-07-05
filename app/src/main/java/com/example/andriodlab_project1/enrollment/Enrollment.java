package com.example.andriodlab_project1.enrollment;

public class Enrollment {

    private int CourseID;
    private String StudentEmail;

    public Enrollment(int courseID, String studentEmail) {
        CourseID = courseID;
        StudentEmail = studentEmail;
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
