package com.example.andriodlab_project1.instructor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class InstructorDataBaseHelper extends SQLiteOpenHelper {
    public InstructorDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE INSTRUCTOR(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT NOT NULL, LASTNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL," +
                "MOBILENUMBER TEXT NOT NULL,ADDRESS TEXT NOT NULL,SPECIALIZATION TEXT NOT NULL,DEGREE TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertInstructor(Instructor instructor) {
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + instructor.getEmail() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
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
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
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
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + email + "\";", null);
        return cursor.moveToFirst();
    }

    public boolean correctSignIn(String email, String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + email + "\" AND PASSWORD = \"" + password + "\";", null);
        return cursor.moveToFirst();
    }

}
