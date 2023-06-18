package com.example.andriodlab_project1.admin;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Admin Table
 */
public class AdminDataBaseHelper extends SQLiteOpenHelper {
    public AdminDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE ADMIN(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertAdmin(Admin admin) {
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM ADMIN WHERE EMAIL = \"" + admin.getEmail() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EMAIL", admin.getEmail());
            contentValues.put("FIRSTNAME", admin.getFirstName());
            contentValues.put("LASTNAME", admin.getLastName());
            contentValues.put("PASSWORD", admin.getPassword());
            sqLiteDatabase.insert("ADMIN", null, contentValues);
            return true;
        }
        return false;
    }



    public Admin getAdminByEmail(String email){
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        Admin admin = new Admin();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM ADMIN WHERE EMAIL = \"" + email + "\";", null);
        if (cursor.moveToFirst()) {
            admin.setEmail(cursor.getString(0));
            admin.setFirstName(cursor.getString(1));
            admin.setLastName(cursor.getString(2));
            admin.setPassword(cursor.getString(3));
        }
        return admin;
    }


    public boolean isRegistered(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ADMIN WHERE EMAIL = \"" + email + "\";", null);
        return cursor.moveToFirst();
    }

    public boolean correctSignIn(String email, String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ADMIN WHERE EMAIL = \"" + email + "\" AND PASSWORD = \"" + password + "\";", null);
        return cursor.moveToFirst();
    }

//    public Cursor RemoveCourse(int CourseID){
//
//    }

}
