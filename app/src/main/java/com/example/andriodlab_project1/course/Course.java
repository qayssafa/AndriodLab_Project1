package com.example.andriodlab_project1.course;

import java.sql.Blob;
import java.util.ArrayList;

public class Course {
    private int CorseID;
    private String CourseTitle;
    private ArrayList<String>CourseMainTopics;
    private String Prerequisites;
    private Blob photo;

    public Course(int corseID, String courseTitle, ArrayList<String> courseMainTopics, String prerequisites, Blob photo) {
        CorseID = corseID;
        CourseTitle = courseTitle;
        CourseMainTopics = courseMainTopics;
        Prerequisites = prerequisites;
        this.photo = photo;
    }

    public Course() {
    }

    public int getCorseID() {
        return CorseID;
    }

    public String getCourseTitle() {
        return CourseTitle;
    }

    public ArrayList<String> getCourseMainTopics() {
        return CourseMainTopics;
    }

    public String getPrerequisites() {
        return Prerequisites;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setCorseID(int corseID) {
        CorseID = corseID;
    }

    public void setCourseTitle(String courseTitle) {
        CourseTitle = courseTitle;
    }

    public void setCourseMainTopics(ArrayList<String> courseMainTopics) {
        CourseMainTopics = courseMainTopics;
    }

    public void setPrerequisites(String prerequisites) {
        Prerequisites = prerequisites;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }
}
