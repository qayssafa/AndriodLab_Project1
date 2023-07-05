package com.example.andriodlab_project1.notification;

public class Notification {
    private String studentEmail;
    private String message;
    private String timestamp;

    public Notification(String studentEmail, String message, String timestamp) {
        this.studentEmail = studentEmail;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Notification() {
    }

    public String getStudentId() {
        return studentEmail;
    }

    public void setStudentId(String studentId) {
        this.studentEmail = studentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
