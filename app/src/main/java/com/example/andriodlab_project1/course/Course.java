package com.example.andriodlab_project1.course;

import java.sql.Blob;
import java.util.ArrayList;

public class Course {
    private int CorseID;
    private String CourseTitle;
    private ArrayList<String> CourseMainTopics;
    private ArrayList<Integer> Prerequisites;
    private Blob photo;

    public Course(String courseTitle, ArrayList<String> courseMainTopics, ArrayList<Integer> prerequisites, Blob photo) {
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

    public ArrayList<Integer> getPrerequisites() {
        return Prerequisites;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setCourseID(int courseID) {
        CorseID = courseID;
    }

    public void setCourseTitle(String courseTitle) {
        CourseTitle = courseTitle;
    }

    public void setCourseMainTopics(ArrayList<String> courseMainTopics) {
        CourseMainTopics = courseMainTopics;
    }

    public void setPrerequisites(ArrayList<Integer> prerequisites) {
        Prerequisites = prerequisites;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }
}
