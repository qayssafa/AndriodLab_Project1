package com.example.andriodlab_project1.course_for_registration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AvailableCourse {
  private int courseId;
  private String registrationDeadline;
  private String courseStartDate;
  private String courseSchedule;
  private String venue;

  public AvailableCourse(int courseId, String registrationDeadline,
                         String courseStartDate, String courseSchedule, String venue) {
    this.courseId = courseId;
    this.registrationDeadline = registrationDeadline;
    this.courseStartDate = courseStartDate;
    this.courseSchedule = courseSchedule;
    this.venue = venue;
  }

  public int getCourseId() {
    return courseId;
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

  public void setCourseId(int courseId) {
    this.courseId = courseId;
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
