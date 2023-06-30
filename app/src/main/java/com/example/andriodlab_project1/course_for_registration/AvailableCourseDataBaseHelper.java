package com.example.andriodlab_project1.course_for_registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andriodlab_project1.admin.Admin;
import com.example.andriodlab_project1.common.DataBaseHelper;
import com.example.andriodlab_project1.course.Course;
import com.example.andriodlab_project1.course.CourseDataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kotlin.Triple;

public class AvailableCourseDataBaseHelper {

    private DataBaseHelper dbHelper;
    private CourseDataBaseHelper courseDataBaseHelper;

    public AvailableCourseDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("AvailableCourse")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE AvailableCourse (" +
                    "registration_Number INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COURSE_ID INTEGER, " +
                    "instructor_email TEXT, " + // Add instructor_name column
                    "registration_deadline TEXT, " +
                    "number_of_student INTEGER," +
                    "instructor_name TEXT, " +
                    "course_start_date TEXT, " +
                    "course_schedule TEXT, " +
                    "venue TEXT, " +
                    "FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID) ON DELETE CASCADE, " +
                    "FOREIGN KEY (instructor_email) REFERENCES INSTRUCTOR(EMAIL) ON DELETE CASCADE" +
                    ")");
        }
    }

    public boolean isTableCreatedFirstTime(String tableName) {
        boolean isFirstTime = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);
        if (cursor != null) {
            if (!cursor.moveToFirst()) {
                // Table doesn't exist
                isFirstTime = true;
            }
            cursor.close();
        }
        db.close();
        return isFirstTime;
    }

    public boolean insertAvailableCourse(AvailableCourse availableCourse, String instructorEmail, int numberOfStudent) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get the instructor name based on the provided instructor email
        String instructorName = getInstructorName(instructorEmail);
        ContentValues values = new ContentValues();
        values.put("COURSE_ID", availableCourse.getCourseId());
        values.put("instructor_email", instructorEmail);
        values.put("registration_deadline", availableCourse.getRegistrationDeadline());
        values.put("instructor_name", instructorName);
        values.put("number_of_student", numberOfStudent);
        values.put("course_start_date", availableCourse.getCourseStartDate());
        values.put("course_schedule", availableCourse.getCourseSchedule());
        values.put("venue", availableCourse.getVenue());
        long rowId = db.insert("AvailableCourse", null, values);
        if (rowId != -1) {
            // Successful insertion
            return true;
        } else {
            // Failed insertion
            return false;
        }
    }

    public List<Triple<AvailableCourse, String, Integer>> getAvailableCourseByCourse_Id(int course_id) {

        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        List<Triple<AvailableCourse, String, Integer>> availableCourses = new ArrayList<>();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM AvailableCourse AC " +
                "JOIN Course C ON AC.COURSE_ID = C.COURSE_ID " +
                "WHERE AC.COURSE_ID = \"" + course_id + "\";", null);

        if (cursor.moveToFirst()) {
            do {
                int courseID = cursor.getInt(1);
                String registrationDeadline = cursor.getString(3);
                int numberOfStudents = cursor.getInt(4);
                String instructorName = cursor.getString(5);
                String courseStartDate = cursor.getString(6);
                String courseSchedule = cursor.getString(7);
                String venue = cursor.getString(8);
                AvailableCourse availableCourse = new AvailableCourse(courseID, registrationDeadline, courseStartDate, courseSchedule, venue);
                Triple<AvailableCourse, String, Integer> courseInfo = new Triple<>(availableCourse, instructorName, numberOfStudents);
                availableCourses.add(courseInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
        return availableCourses;
    }

    private String getInstructorName(String instructorEmail) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT FIRSTNAME, LASTNAME FROM INSTRUCTOR WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{instructorEmail});
        String instructorName = "";
        if (cursor.moveToFirst()) {
            int firstNameIndex = cursor.getColumnIndex("FIRSTNAME");
            int lastNameIndex = cursor.getColumnIndex("LASTNAME");

            if (firstNameIndex >= 0 && lastNameIndex >= 0) {
                String firstName = cursor.getString(firstNameIndex);
                String lastName = cursor.getString(lastNameIndex);
                instructorName = firstName + " " + lastName;
            }
        }
        cursor.close();
        return instructorName;
    }

    public List<Map.Entry<String, String>> getAllCoursesForRegistration() {
        List<Map.Entry<String, String>> courses = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor=null;
        try {
            cursor = db.rawQuery("SELECT COURSE_ID FROM AvailableCourse", null);
            if (cursor.moveToFirst()) {
                do {
                    if (CourseDataBaseHelper.isCourseExists(Integer.parseInt(cursor.getString(0)))){
                        courses.add(new AbstractMap.SimpleEntry<>(cursor.getString(0),CourseDataBaseHelper.getCourseName(cursor.getInt(0))));
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return courses;
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }
    public List<Map.Entry<String, String>> getAllCoursesAreAvailableForRegistration() {
        List<Map.Entry<String, String>> courses = new ArrayList<>();
        long currentTime = new Date().getTime();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentTimeString = dateFormat.format(new Date(currentTime));
        Cursor cursor=null;
        try {
            cursor = db.rawQuery("SELECT COURSE_ID,registration_deadline FROM AvailableCourse WHERE datetime(registration_deadline) > datetime('" + currentTimeString + "')", null);
            if (cursor.moveToFirst()) {
                do {
                    courses.add(new AbstractMap.SimpleEntry<>(cursor.getString(0),cursor.getString(1)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return courses;
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }
    public List<Map.Entry<String, String>> getAllCoursesAreFinished() {
        List<Map.Entry<String, String>> courses = new ArrayList<>();
        long currentTime = new Date().getTime();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentTimeString = dateFormat.format(new Date(currentTime));
        Cursor cursor=null;
        try {
            cursor = db.rawQuery("SELECT COURSE_ID FROM AvailableCourse WHERE datetime(registration_deadline) < datetime('" + currentTimeString + "')", null);
            if (cursor.moveToFirst()) {
                do {
                    courses.add(new AbstractMap.SimpleEntry<>(cursor.getString(0),CourseDataBaseHelper.getCourseName(cursor.getInt(0))));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return courses;
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }
}



