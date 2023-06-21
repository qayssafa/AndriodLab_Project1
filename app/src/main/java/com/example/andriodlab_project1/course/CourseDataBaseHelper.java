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
            sqLiteDatabase.execSQL("CREATE TABLE COURSE(COURSE_ID INTEGER PRIMARY KEY AUTOINCREMENT, Course_Title TEXT, Course_Main_Topics TEXT, " +
                    "Prerequisites TEXT NOT NULL,Photo Blob)");
        }
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
        boolean isDeleted = rowsAffected > 0;
        return isDeleted;
    }
    public boolean updateCourse(Course course) {
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
            //sqLiteDatabase.update("COURSE", null, contentValues);
            return true;
        }
        return false;
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
