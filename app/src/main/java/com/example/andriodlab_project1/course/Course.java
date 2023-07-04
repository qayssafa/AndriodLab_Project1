package com.example.andriodlab_project1.course;

import java.sql.Blob;
import java.util.ArrayList;

public class Course {
    private int CorseID;
    private String CourseTitle;
    private ArrayList<String> CourseMainTopics;
    private ArrayList<String> Prerequisites;
    private byte[] photo;

    public Course(String courseTitle, ArrayList<String> courseMainTopics, ArrayList<String> prerequisites, byte[] photo) {
        CourseTitle = courseTitle;
        CourseMainTopics = courseMainTopics;
        Prerequisites = prerequisites;
        this.photo = photo;
    }

    public Course() {
    }

    public int getCourseID() {
        return CorseID;
    }

    public String getCourseTitle() {
        return CourseTitle;
    }

    public ArrayList<String> getCourseMainTopics() {
        return CourseMainTopics;
    }

    public ArrayList<String> getPrerequisites() {
        return Prerequisites;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setCourseID(int courseID) {
        this.CorseID = courseID;
    }

    public void setCourseTitle(String courseTitle) {
        CourseTitle = courseTitle;
    }

    public void setCourseMainTopics(ArrayList<String> courseMainTopics) {
        CourseMainTopics = courseMainTopics;
    }

    public void setPrerequisites(ArrayList<String> prerequisites) {
        Prerequisites = prerequisites;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
