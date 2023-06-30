package com.example.andriodlab_project1.instructor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.andriodlab_project1.common.DataBaseHelper;

public class InstructorDataBaseHelper {
    private DataBaseHelper dbHelper;
    public InstructorDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("INSTRUCTOR")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE INSTRUCTOR(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT NOT NULL, LASTNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL," +
                    "MOBILENUMBER TEXT NOT NULL,ADDRESS TEXT NOT NULL,SPECIALIZATION TEXT NOT NULL,DEGREE TEXT NOT NULL)");
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

    public boolean insertInstructor(Instructor instructor) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + instructor.getEmail() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EMAIL", instructor.getEmail());
            contentValues.put("FIRSTNAME", instructor.getFirstName());
            contentValues.put("LASTNAME", instructor.getLastName());
            contentValues.put("PASSWORD", instructor.getPassword());
            contentValues.put("MOBILENUMBER", instructor.getMobileNumber());
            contentValues.put("ADDRESS", instructor.getAddress());
            contentValues.put("SPECIALIZATION", instructor.getSpecialization());
            contentValues.put("DEGREE", instructor.getDegree());
            sqLiteDatabase.insert("INSTRUCTOR", null, contentValues);
            return true;
        }
        return false;
    }



    public Instructor getInstructorByEmail(String email){
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Instructor instructor = new Instructor();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + email + "\";", null);
        if (cursor.moveToFirst()) {
            instructor.setEmail(cursor.getString(0));
            instructor.setFirstName(cursor.getString(1));
            instructor.setLastName(cursor.getString(2));
            instructor.setPassword(cursor.getString(3));
            instructor.setMobileNumber(cursor.getString(4));
            instructor.setAddress(cursor.getString(5));
            instructor.setSpecialization(cursor.getString(6));
            instructor.setDegree(cursor.getString(7));
        }
        return instructor;
    }


    public boolean isRegistered(String email){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + email + "\";", null);
        return cursor.moveToFirst();
    }

    public boolean correctSignIn(String email, String password){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + email + "\" AND PASSWORD = \"" + password + "\";", null);
        return cursor.moveToFirst();
    }
    public String getEmailByFullName(String firstName, String lastName) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT EMAIL FROM INSTRUCTOR WHERE FIRSTNAME = ? AND LASTNAME = ?", new String[]{firstName, lastName});
        String email = null;
        if (cursor.moveToFirst()) {
            email = cursor.getString(0);
        }
        cursor.close();
        sqLiteDatabase.close();
        return email;
    }


}
