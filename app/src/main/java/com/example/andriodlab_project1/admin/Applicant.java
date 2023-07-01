package com.example.andriodlab_project1.admin;

public class Applicant {
        private int applicantId;
        private int courseId;
        private String email;
        private String status;

        public Applicant() {
            // Default constructor
        }

        public Applicant(int courseId, String email, String status) {
            this.courseId = courseId;
            this.email = email;
            this.status = status;
        }

        public int getApplicantId() {
            return applicantId;
        }

        public void setApplicantId(int applicantId) {
            this.applicantId = applicantId;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
