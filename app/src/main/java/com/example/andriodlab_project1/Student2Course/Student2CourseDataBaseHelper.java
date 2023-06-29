package com.example.andriodlab_project1.Student2Course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.andriodlab_project1.common.DataBaseHelper;
import com.example.andriodlab_project1.student.Student;

public class Student2CourseDataBaseHelper {
    private DataBaseHelper dbHelper;

    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("STUDENT2COURSE")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE STUDENT2COURSE(EMAIL TEXT PRIMARY KEY, COURSE_ID INTEGER PRIMARY KEY,FOREIGN KEY (EMAIL) REFERENCES STUDENT(EMAIL)," +
                    "FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID))");
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

    public boolean insertStudent2Course(Student2COURSE s2c) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM STUDENT2COURSE", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("STUDENTEMAIL", s2c.getStudentEmail());
            contentValues.put("COURSEID",s2c.getCourseID());
            sqLiteDatabase.insert("STUDENT2COURSE   ", null, contentValues);
            return true;
        }
        return false;
    }
}
