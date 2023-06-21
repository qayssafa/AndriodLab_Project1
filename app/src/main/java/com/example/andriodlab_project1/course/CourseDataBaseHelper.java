package com.example.andriodlab_project1.course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andriodlab_project1.admin.Admin;
import com.example.andriodlab_project1.common.DataBaseHelper;

public class CourseDataBaseHelper {
    private DataBaseHelper dbHelper;
    public CourseDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM COURSE " , null);

        if (cursor.getColumnCount() != 0) {
            // Table already exists
            cursor.close();
        } else {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            // Create the table
            sqLiteDatabase.execSQL("CREATE TABLE COURSE(CORSE_ID INTEGER PRIMARY KEY AUTOINCREMENT, Course_Title TEXT, Course_Main_Topics TEXT, " +
                    "Prerequisites TEXT NOT NULL,Photo Blob)");
        }
    }
}
