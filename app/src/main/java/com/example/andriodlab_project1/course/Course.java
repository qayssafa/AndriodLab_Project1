package com.example.andriodlab_project1.course;

import java.sql.Blob;
import java.util.ArrayList;

public class Course {
    private int CorseID;
    private String CourseTitle;
    private ArrayList<String>CourseMainTopics;
    private ArrayList<String>Prerequisites;
    private Blob photo;

    public Course(int courseId, String courseTitle, ArrayList<String> courseMainTopics, ArrayList<String> prerequisites, Blob photo) {
        CorseID = courseId;
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


    public ArrayList<String> getPrerequisites() {
        return Prerequisites;
    }

    public Blob getPhoto() {
        return photo;
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

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }
}
