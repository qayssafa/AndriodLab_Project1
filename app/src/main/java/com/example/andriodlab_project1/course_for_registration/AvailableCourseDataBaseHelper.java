package com.example.andriodlab_project1.course_for_registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andriodlab_project1.common.DataBaseHelper;

public class AvailableCourseDataBaseHelper{

    private DataBaseHelper dbHelper;
    public AvailableCourseDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("AvailableCourse")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE AvailableCourse (" +
                    "course_id INTEGER PRIMARY KEY, " +
                    "instructor_email TEXT, " + // Add instructor_name column
                    "registration_deadline TEXT, " +
                    "instructor_name TEXT, " +
                    "course_start_date TEXT, " +
                    "course_schedule TEXT, " +
                    "venue TEXT, " +
                    "FOREIGN KEY (course_id) REFERENCES COURSE(COURSE_ID), " +
                    "FOREIGN KEY (instructor_email) REFERENCES INSTRUCTOR(EMAIL)" +
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
    public void insertAvailableCourse(AvailableCourse availableCourse,String instructorEmail) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get the instructor name based on the provided instructor email
        String instructorName = getInstructorName(instructorEmail);

        ContentValues values = new ContentValues();
        values.put("course_id",availableCourse.getCourseId());
        values.put("instructor_email", instructorEmail);
        values.put("registration_deadline", availableCourse.getRegistrationDeadline());
        values.put("instructor_name", instructorName);
        values.put("course_start_date", availableCourse.getCourseStartDate());
        values.put("course_schedule", availableCourse.getCourseSchedule());
        values.put("venue", availableCourse.getVenue());

        long rowId = db.insert("AvailableCourse", null, values);

        if (rowId != -1) {
            // Successful insertion
        } else {
            // Failed insertion
        }
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

}








