package com.example.andriodlab_project1.enrollment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andriodlab_project1.common.DataBaseHelper;
import com.example.andriodlab_project1.course.Course;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnrollmentDataBaseHelper {
    private DataBaseHelper dbHelper;
    public EnrollmentDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("enrollments")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE enrollments(enrollment_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "EMAIL TEXT, " +
                    "COURSE_ID INTEGER,FOREIGN KEY (EMAIL) REFERENCES STUDENT(EMAIL) ON DELETE CASCADE," +
                    "FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID) ON DELETE CASCADE)");
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

    public boolean insertStudent2Course(Enrollment s2c) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM enrollments", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EMAIL", s2c.getStudentEmail());
            contentValues.put("COURSE_ID",s2c.getCourseID());
            sqLiteDatabase.insert("enrollments", null, contentValues);
            return true;
        }
        return false;
    }
    public List<Map.Entry<Integer, String>> getCoursesTakenByStudent(String email) {
        List<Map.Entry<Integer, String>> coursesTaken = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map.Entry<Integer, String> entry;
        String selectQuery = "SELECT c.COURSE_ID,c.Course_Title FROM courses c INNER JOIN enrollments e ON c.course_id = e.course_id WHERE e.email = ?";
        String[] selectionArgs = { email };
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
        while (cursor.moveToNext()) {
            int  COURSE_ID = cursor.getInt(0);
            String  courseTitle = cursor.getString(1);
            entry = new AbstractMap.SimpleEntry<>(COURSE_ID, courseTitle);
            coursesTaken.add(entry);
        }
        cursor.close();
        return coursesTaken;
    }
    public ArrayList<String> getStudentsByCourseId(int courseId) {
        ArrayList<String> students = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT s.EMAIL FROM STUDENT s INNER JOIN enrollments e ON s.EMAIL = e.EMAIL WHERE e.COURSE_ID = ?";
        String[] selectionArgs = {String.valueOf(courseId)};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
        while (cursor.moveToNext()) {
            String  courseTitle = cursor.getString(0);
            students.add(courseTitle);
        }
        cursor.close();
        return students;
    }

}
