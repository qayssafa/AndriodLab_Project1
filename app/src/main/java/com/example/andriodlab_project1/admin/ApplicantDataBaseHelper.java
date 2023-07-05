package com.example.andriodlab_project1.admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andriodlab_project1.common.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ApplicantDataBaseHelper {
    private final DataBaseHelper dbHelper;

    public ApplicantDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("APPLICANT")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            // Create the table
            sqLiteDatabase.execSQL("CREATE TABLE APPLICANT (" +
                    "applicantId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COURSE_ID INTEGER," +
                    "EMAIL TEXT," +
                    "status TEXT," +
                    "FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID) ON DELETE CASCADE," +
                    "FOREIGN KEY (EMAIL) REFERENCES STUDENT(EMAIL) ON DELETE CASCADE" +
                    ")");
        }
    }

    public boolean isTableCreatedFirstTime(String tableName) {
        boolean isFirstTime = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);
        if (cursor != null) {
            if (!cursor.moveToFirst()) {
                isFirstTime = true;
            }
            cursor.close();
        }
        db.close();
        return isFirstTime;
    }

    public boolean insertApplicant(Applicant applicant) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("COURSE_ID", applicant.getCourseId());
        values.put("EMAIL", applicant.getEmail());
        values.put("status", applicant.getStatus());
        long result = db.insert("APPLICANT", null, values);
        db.close();
        if (result == -1) {
            // Failed to insert applicant
            return false;
        } else {
            // Applicant inserted successfully
            return true;
        }
    }

    public List<Applicant> getAllApplicants() {
        List<Applicant> applicants = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM APPLICANT", null);
        if (cursor.moveToFirst()) {
            do {
                int applicantId = cursor.getInt(0);
                int courseId = cursor.getInt(1);
                String email = cursor.getString(2);
                String status = cursor.getString(3);

                Applicant applicant = new Applicant(courseId, email, status);
                applicant.setApplicantId(applicantId);
                applicants.add(applicant);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return applicants;
    }

    public boolean updateApplicantStatus(int applicantId, String newStatus) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status", newStatus);

        int rowsAffected = db.update("APPLICANT", values, "applicantId=?", new String[]{String.valueOf(applicantId)});

        db.close();

        return rowsAffected > 0;
    }

    public String getApplicantStatus(int applicantId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"status"};
        String selection = "applicantId=?";
        String[] selectionArgs = {String.valueOf(applicantId)};

        Cursor cursor = db.query("APPLICANT", projection, selection, selectionArgs, null, null, null);

        String status = null;
        if (cursor.moveToFirst()) {
            status = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return status;
    }

    public List<Integer> getAllApplicationIds() {
        List<Integer> applicationIds = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"applicantId"};
        String selection = "status = ?";
        String[] selectionArgs = {" "}; // Filter for empty status
        Cursor cursor = db.query("APPLICANT", projection, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int applicantId = cursor.getInt(0);
            applicationIds.add(applicantId);
        }

        cursor.close();
        db.close();

        return applicationIds;
    }

    public List<Integer> getApplicantIdsByEmail(String email) {
        List<Integer> applicantIds = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"applicantId"};
        String selection = "EMAIL = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query("APPLICANT", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int applicantId = cursor.getInt(0);
                applicantIds.add(applicantId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return applicantIds;
    }

    public Applicant getApplicantById(int applicantId) {
        Applicant applicant = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"COURSE_ID", "EMAIL", "status"};
        String selection = "applicantId = ?";
        String[] selectionArgs = {String.valueOf(applicantId)};
        Cursor cursor = db.query("APPLICANT", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int courseId = cursor.getInt(0);
            String email = cursor.getString(1);
            String status = cursor.getString(2);
            // Create a new Applicant object with the retrieved information
            applicant = new Applicant(courseId, email, status);
            applicant.setApplicantId(applicantId);
        }

        cursor.close();
        db.close();

        return applicant;
    }
}
