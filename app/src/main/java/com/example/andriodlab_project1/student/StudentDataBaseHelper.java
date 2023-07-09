package com.example.andriodlab_project1.student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.andriodlab_project1.common.DataBaseHelper;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StudentDataBaseHelper {
    private final DataBaseHelper dbHelper;

    public StudentDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("STUDENT")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE STUDENT(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT NOT NULL, LASTNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL,MOBILENUMBER TEXT NOT NULL,ADDRESS TEXT NOT NULL)");
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

    public boolean insertStudent(Student student) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM STUDENT WHERE EMAIL = \"" + student.getEmail() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EMAIL", student.getEmail());
            contentValues.put("FIRSTNAME", student.getFirstName());
            contentValues.put("LASTNAME", student.getLastName());
            contentValues.put("PASSWORD", student.getPassword());
            contentValues.put("MOBILENUMBER", student.getMobileNumber());
            contentValues.put("ADDRESS", student.getAddress());
            sqLiteDatabase.insert("STUDENT", null, contentValues);
            return true;
        }
        return false;
    }


    public Student getStudentByEmail(String email) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Student student = new Student();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM STUDENT  WHERE EMAIL = \"" + email + "\";", null);
        if (cursor.moveToFirst()) {
            student.setEmail(cursor.getString(0));
            student.setFirstName(cursor.getString(1));
            student.setLastName(cursor.getString(2));
            student.setPassword(cursor.getString(3));
            student.setMobileNumber(cursor.getString(4));
            student.setAddress(cursor.getString(5));
        }
        return student;
    }


    public boolean isRegistered(String email) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM STUDENT WHERE EMAIL = \"" + email + "\";", null);
        return cursor.moveToFirst();
    }

    public boolean correctSignIn(String email, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM STUDENT WHERE EMAIL = \"" + email + "\" AND PASSWORD = \"" + password + "\";", null);
        return cursor.moveToFirst();
    }

    public List<String> getAllStudents() {
        List<String> students = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT EMAIL FROM STUDENT";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            String studentId = cursor.getString(0);
            students.add(studentId);
        }
        cursor.close();
        return students;
    }

    public List<Map.Entry<String, String>> getAllStudentEmailAndName() {
        List<Map.Entry<String, String>> emailAndNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT EMAIL, FIRSTNAME, LASTNAME FROM STUDENT", null);
        if (cursor.moveToFirst()) {
            do {
                String email = cursor.getString(0);
                String name = cursor.getString(1) + " " + cursor.getString(2);
                emailAndNameList.add(new AbstractMap.SimpleEntry<>(email, name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return emailAndNameList;
    }


}
