package com.example.andriodlab_project1.student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.andriodlab_project1.common.DataBaseHelper;
import com.example.andriodlab_project1.instructor.Instructor;

import java.io.ByteArrayOutputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StudentDataBaseHelper {
    private final DataBaseHelper dbHelper;
    private byte[] imageBytes;
    private ByteArrayOutputStream objectByteArrayOutputStream;

    public StudentDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("STUDENT")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE STUDENT(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT NOT NULL, LASTNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL,MOBILENUMBER TEXT NOT NULL,ADDRESS TEXT NOT NULL,PHOTO BLOB)");
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
            Bitmap imageStore = student.getPhoto();
            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageStore.compress(Bitmap.CompressFormat.PNG,100,objectByteArrayOutputStream);
            imageBytes = objectByteArrayOutputStream.toByteArray();
            contentValues.put("PHOTO",imageBytes);
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
            byte[] conv = cursor.getBlob(6);
            Bitmap ret = BitmapFactory.decodeByteArray(conv, 0, conv.length);
            student.setPhoto(ret);
        }
        return student;
    }

    public Bitmap getImage(String Student_Email) {
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT PHOTO FROM STUDENT WHERE EMAIL = ?", new String[]{Student_Email});
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



    public int updateStudent(Student student) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRSTNAME", student.getFirstName());
        contentValues.put("LASTNAME", student.getLastName());
        contentValues.put("PASSWORD", student.getPassword());
        contentValues.put("MOBILENUMBER", student.getMobileNumber());
        contentValues.put("ADDRESS", student.getAddress());

        String email = student.getEmail();
        return sqLiteDatabase.update("STUDENT", contentValues, "EMAIL = ?", new String[]{email});
    }




}
