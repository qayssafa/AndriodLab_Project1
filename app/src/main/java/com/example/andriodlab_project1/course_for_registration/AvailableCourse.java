package com.example.andriodlab_project1.course_for_registration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AvailableCourse {
  private int courseId;
  private Date registrationDeadline;
  private Date courseStartDate;
  private String courseSchedule;
  private String venue;

  public AvailableCourse(int courseId, Date registrationDeadline,
                         Date courseStartDate, String courseSchedule, String venue) {
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
  return formatDate(registrationDeadline);
  }


  public String getCourseStartDate() {

    return formatDate(courseStartDate);
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


  public void setRegistrationDeadline(Date registrationDeadline) {
    this.registrationDeadline = registrationDeadline;
  }


  public void setCourseStartDate(Date courseStartDate) {
    this.courseStartDate = courseStartDate;
  }

  public void setCourseSchedule(String courseSchedule) {
    this.courseSchedule = courseSchedule;
  }

  public void setVenue(String venue) {
    this.venue = venue;
  }
  public String formatDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    return sdf.format(date);
  }
}
