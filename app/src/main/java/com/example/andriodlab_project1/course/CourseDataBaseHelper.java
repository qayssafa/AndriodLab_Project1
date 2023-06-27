package com.example.andriodlab_project1.course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andriodlab_project1.admin.Admin;
import com.example.andriodlab_project1.common.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CourseDataBaseHelper {
    private DataBaseHelper dbHelper;
    public CourseDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("COURSE")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            // Create the table
            sqLiteDatabase.execSQL("CREATE TABLE COURSE(COURSE_ID INTEGER PRIMARY KEY AUTOINCREMENT, Course_Title TEXT, Course_Main_Topics TEXT, " +
                    "Prerequisites TEXT NOT NULL,Photo Blob)");        }
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
    public boolean insertCourse(Course course) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM COURSE WHERE COURSE_ID  = \"" + course.getCorseID() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Course_Title",course.getCourseTitle());
            StringBuilder stringBuilder = new StringBuilder();
            for (String topic : course.getCourseMainTopics()) {
                stringBuilder.append(topic).append(",");
            }
            contentValues.put("Course_Main_Topics", stringBuilder.toString());
            contentValues.put("Prerequisites", course.getPrerequisites());
            //Photo
            sqLiteDatabase.insert("COURSE", null, contentValues);
            return true;
        }
        return false;
    }
    public boolean deleteCourse(int courseId){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int rowsAffected=sqLiteDatabase.delete("COURSE", "COURSE_ID = ?", new String[]{String.valueOf(courseId)});
        sqLiteDatabase.close();
        if (rowsAffected > 0) {
            return true;
            //toastMessage
            // Update was successful
            // You can perform any additional actions or show a success message
        } else {
            return false;
            //toastMessage
            // Update failed
            // Handle the case where the course with the given courseId doesn't exist or other errors occurred
        }
    }
    public Boolean updateCourse(Course course) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM COURSE WHERE COURSE_ID  = \"" + course.getCorseID() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Course_Title", course.getCourseTitle());
            StringBuilder stringBuilder = new StringBuilder();
            for (String topic : course.getCourseMainTopics()) {
                stringBuilder.append(topic).append(",");
            }
            contentValues.put("Course_Main_Topics", stringBuilder.toString());
            contentValues.put("Prerequisites", course.getPrerequisites());
            //Photo
            int rowsAffected = sqLiteDatabaseR.update("COURSE", contentValues, "COURSE_ID = ?", new String[]{String.valueOf(course.getCorseID())});
            sqLiteDatabaseR.close();
            if (rowsAffected > 0) {
                return true;
                //toastMessage
                // Update was successful
                // You can perform any additional actions or show a success message
            } else {
                return false;
                //toastMessage
                // Update failed
                // Handle the case where the course with the given courseId doesn't exist or other errors occurred
            }
        }
        return false;
    }
    public boolean isCourseExists(int courseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM COURSE WHERE COURSE_ID = \"" + courseId + "\";", null);
        boolean isCourseExists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return isCourseExists;
    }

    public List<String> getAllCourses() {
        List<String> courses = new ArrayList<String>();
        SQLiteDatabase db  = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT c.Course_Title FROM COURSE c",null);

        if (cursor.moveToFirst()) {
            do {
                courses.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return courses;
    }
    /**
     * public String[] getArray() {
     *         SQLiteDatabase db = this.getReadableDatabase();
     *         String selectQuery = "SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME;
     *         Cursor cursor = db.rawQuery(selectQuery, null);
     *         String[] array = null;
     *         if (cursor.moveToFirst()) {
     *             String serializedArray = cursor.getString(0);
     *             array = serializedArray.split(",");
     *         }
     *         cursor.close();
     *         db.close();
     *         return array;
     *     }
     */
}
