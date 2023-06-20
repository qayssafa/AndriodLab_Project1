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
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("CREATE TABLE INSTRUCTOR(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT NOT NULL, LASTNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL," +
                "MOBILENUMBER TEXT NOT NULL,ADDRESS TEXT NOT NULL,SPECIALIZATION TEXT NOT NULL,DEGREE TEXT NOT NULL)");
    }

    public boolean insertInstructor(Instructor instructor) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + instructor.getEmail() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EMAIL", instructor.getEmail());
            contentValues.put("FIRST NAME", instructor.getFirstName());
            contentValues.put("LAST NAME", instructor.getLastName());
            contentValues.put("PASSWORD", instructor.getPassword());
            contentValues.put("MOBILE NUMBER", instructor.getMobileNumber());
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

}
