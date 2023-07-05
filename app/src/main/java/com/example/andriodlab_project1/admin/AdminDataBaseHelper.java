package com.example.andriodlab_project1.admin;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.andriodlab_project1.common.DataBaseHelper;

/**
 * Admin Table
 */
public class AdminDataBaseHelper {

    private final DataBaseHelper dbHelper;
    public AdminDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("ADMIN")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            // Create the table
            sqLiteDatabase.execSQL("CREATE TABLE ADMIN(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT NOT NULL)");        } else {
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
    public boolean insertAdmin(Admin admin) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM ADMIN WHERE EMAIL = \"" + admin.getEmail() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
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
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
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
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ADMIN WHERE EMAIL = \"" + email + "\";", null);
        return cursor.moveToFirst();
    }

    public boolean correctSignIn(String email, String password){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ADMIN WHERE EMAIL = \"" + email + "\" AND PASSWORD = \"" + password + "\";", null);
        return cursor.moveToFirst();
    }

}
