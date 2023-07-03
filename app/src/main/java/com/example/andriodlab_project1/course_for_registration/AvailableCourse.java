package com.example.andriodlab_project1.course_for_registration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AvailableCourse {
  private int courseId;
  private int reg;

  private String registrationDeadline;
  private String courseStartDate;
  private String courseEndDate;
  private String courseSchedule;
  private String venue;

  public AvailableCourse(int courseId, String registrationDeadline, String courseStartDate, String courseSchedule, String venue, String courseEndDate) {
    this.courseId = courseId;
    this.registrationDeadline = registrationDeadline;
    this.courseStartDate = courseStartDate;
    this.courseSchedule = courseSchedule;
    this.courseEndDate = courseEndDate;
    this.venue = venue;
  }

  public String getCourseEndDate() {
    return courseEndDate;
  }

  public void setCourseEndDate(String courseEndDate) {
    this.courseEndDate = courseEndDate;
  }

  public int getCourseId() {
    return courseId;
  }

  public void setCourseId(int courseId) {
    this.courseId = courseId;
  }

  public String getRegistrationDeadline() {
  return registrationDeadline;
  }


  public String getCourseStartDate() {

    return courseStartDate;
  }

  public String getCourseSchedule() {
    return courseSchedule;
  }

  public String getVenue() {
    return venue;
  }
  public int getReg() {
    return reg;
  }

  public void setReg(int reg) {
    this.reg = reg;
  }

  public void setRegistrationDeadline(String registrationDeadline) {
    this.registrationDeadline = registrationDeadline;
  }
  public void setCourseStartDate(String courseStartDate) {
    this.courseStartDate = courseStartDate;
  }

  public void setCourseSchedule(String courseSchedule) {
    this.courseSchedule = courseSchedule;
  }

  public void setVenue(String venue) {
    this.venue = venue;
  }

}
