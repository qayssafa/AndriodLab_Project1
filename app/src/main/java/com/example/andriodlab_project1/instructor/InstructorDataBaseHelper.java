package com.example.andriodlab_project1.instructor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.andriodlab_project1.common.DataBaseHelper;
import com.example.andriodlab_project1.signup.SignUPMainActivity;

import java.io.ByteArrayOutputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InstructorDataBaseHelper {
    private final DataBaseHelper dbHelper;
    private byte[] imageBytes;
    private ByteArrayOutputStream objectByteArrayOutputStream;

    public InstructorDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("INSTRUCTOR")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE INSTRUCTOR(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT NOT NULL, LASTNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL," +
                    "MOBILE_NUMBER TEXT NOT NULL,ADDRESS TEXT NOT NULL,SPECIALIZATION TEXT NOT NULL,DEGREE TEXT NOT NULL,COURSES_TAUGHT TEXT NOT NULL,PHOTO BLOB)");
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
            contentValues.put("MOBILE_NUMBER", instructor.getMobileNumber());
            contentValues.put("ADDRESS", instructor.getAddress());
            contentValues.put("SPECIALIZATION", instructor.getSpecialization());
            contentValues.put("DEGREE", instructor.getDegree());
            contentValues.put("COURSES_TAUGHT", convertListToString(instructor.getCoursesTaught()));
            Bitmap imageStore = instructor.getPhoto();
            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageStore.compress(Bitmap.CompressFormat.PNG,100,objectByteArrayOutputStream);
            imageBytes = objectByteArrayOutputStream.toByteArray();
            contentValues.put("PHOTO",imageBytes);
            sqLiteDatabase.insert("INSTRUCTOR", null, contentValues);
            return true;
        }
        return false;
    }


    public Instructor getInstructorByEmail(String email) {
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
            instructor.setCoursesTaught(splitStringToList(cursor.getString(8)));
        }
        return instructor;
    }

    public Bitmap getImage(String Instructor_Email) {
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT PHOTO FROM INSTRUCTOR WHERE EMAIL = ?", new String[]{Instructor_Email});
        Bitmap ret = null;
        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                byte[] conv = c.getBlob(0);
                ret = BitmapFactory.decodeByteArray(conv, 0, conv.length);
            }
        }
        return ret;
    }


    public boolean isRegistered(String email) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + email + "\";", null);
        return cursor.moveToFirst();
    }

    public boolean correctSignIn(String email, String password) {
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

    public String convertListToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String element : list) {
            sb.append(element).append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // Remove the last comma
        }
        return sb.toString();
    }

    public List<String> splitStringToList(String input) {
        String[] splitArray = input.split(",");
        return Arrays.asList(splitArray);
    }

    public List<Map.Entry<String, String>> getAllInstructorsEmailAndName() {
        List<Map.Entry<String, String>> emailAndNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT EMAIL, FIRSTNAME, LASTNAME FROM INSTRUCTOR", null);
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
