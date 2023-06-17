package com.example.andriodlab_project1.course;

import java.util.Arrays;

public class Course {

    private String courseNumber;
    private String courseTitle;
    private String[] courseMainTopics;
    private String[] coursePreRequisites;
    private String coursePhoto;

    public Course(String courseNumber, String courseTitle, String[] courseMainTopics, String[] coursePreRequisites, String coursePhoto) {
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
        this.courseMainTopics = courseMainTopics;
        this.coursePreRequisites = coursePreRequisites;
        this.coursePhoto = coursePhoto;
    }

    public Course() {
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String[] getCourseMainTopics() {
        return courseMainTopics;
    }

    public void setCourseMainTopics(String[] courseMainTopics) {
        this.courseMainTopics = courseMainTopics;
    }

    public String[] getCoursePreRequisites() {
        return coursePreRequisites;
    }

    public void setCoursePreRequisites(String[] coursePreRequisites) {
        this.coursePreRequisites = coursePreRequisites;
    }

    public String getCoursePhoto() {
        return coursePhoto;
    }

    public void setCoursePhoto(String coursePhoto) {
        this.coursePhoto = coursePhoto;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseNumber='" + courseNumber + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseMainTopics=" + Arrays.toString(courseMainTopics) +
                ", coursePreRequisites=" + Arrays.toString(coursePreRequisites) +
                ", coursePhoto='" + coursePhoto + '\'' +
                '}';
    }
}
