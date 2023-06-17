package com.example.andriodlab_project1.student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class StudentDataBaseHelper extends SQLiteOpenHelper {

    public StudentDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE STUDENT(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT NOT NULL, LASTNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL,MOBILENUMBER TEXT NOT NULL,ADDRESS TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertStudent(Student student) {
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM STUDENT WHERE EMAIL = \"" + student.getEmail() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EMAIL", student.getEmail());
            contentValues.put("FIRST NAME", student.getFirstName());
            contentValues.put("LAST NAME", student.getLastName());
            contentValues.put("PASSWORD", student.getPassword());
            contentValues.put("MOBILE NUMBER", student.getMobileNumber());
            contentValues.put("ADDRESS", student.getAddress());
            sqLiteDatabase.insert("STUDENT", null, contentValues);
            return true;
        }
        return false;
    }



    public Student getStudentByEmail(String email){
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        Student student  = new Student();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM STUDENT  WHERE EMAIL = \"" + email + "\";", null);
        if (cursor.moveToFirst()) {
            student.setEmail(cursor.getString(0));
            student.setFirstName(cursor.getString(1));
            student.setLastName(cursor.getString(2));
            student.setPassword(cursor.getString(3));
            student.setPassword(cursor.getString(4));
            student.setPassword(cursor.getString(5));
        }
        return student;
    }


    public boolean isRegistered(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM STUDENT WHERE EMAIL = \"" + email + "\";", null);
        return cursor.moveToFirst();
    }

    public boolean correctSignIn(String email, String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM STUDENT WHERE EMAIL = \"" + email + "\" AND PASSWORD = \"" + password + "\";", null);
        return cursor.moveToFirst();
    }

}
