package com.example.andriodlab_project1.enrollment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andriodlab_project1.common.DataBaseHelper;
import com.example.andriodlab_project1.course.Course;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EnrollmentDataBaseHelper {
    private final DataBaseHelper dbHelper;

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
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", s2c.getStudentEmail());
        contentValues.put("COURSE_ID", s2c.getCourseID());
        sqLiteDatabase.insert("enrollments", null, contentValues);
        return true;
    }

    public ArrayList<String> getStudentsByCourseId(int courseId) {
        ArrayList<String> students = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT s.EMAIL FROM STUDENT s INNER JOIN enrollments e ON s.EMAIL = e.EMAIL WHERE e.COURSE_ID = ?";
        String[] selectionArgs = {String.valueOf(courseId)};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
        while (cursor.moveToNext()) {
            String EMAIL = cursor.getString(0);
            students.add(EMAIL);
        }
        cursor.close();
        return students;
    }

    public List<Integer> getCoursesByStudentEmail(String email) {
        List<Integer> courseIds = new ArrayList<>();

        if (!isTableCreatedFirstTime("enrollments")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
            String query = "SELECT COURSE_ID FROM enrollments WHERE EMAIL = ?";
            Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email});
            if (cursor.moveToFirst()) {
                do {
                    int courseId = cursor.getInt(0);
                    courseIds.add(courseId);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return courseIds;
        }
        return courseIds;
    }

}
