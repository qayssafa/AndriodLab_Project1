package com.example.andriodlab_project1.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andriodlab_project1.common.DataBaseHelper;
import com.example.andriodlab_project1.notification.Notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationDataBaseHelper {
    private DataBaseHelper dbHelper;
    public NotificationDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("notifications")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE notifications (notification_id INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, message TEXT" +
                    ", timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, is_read INTEGER DEFAULT 0," +
                    " FOREIGN KEY (EMAIL) REFERENCES STUDENT (EMAIL) ON DELETE CASCADE)");
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
    public void insertNotification(String studentEmail, String message) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String notificationMessage = message;
        ContentValues values = new ContentValues();
        values.put("EMAIL", studentEmail);
        values.put("message", notificationMessage);
        db.insert("notifications", null, values);
    }
    public List<Notification> getNotificationsForStudent(int studentId) {
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT EMAIL,message, timestamp FROM notifications WHERE student_id = ?";
        String[] selectionArgs = {String.valueOf(studentId)};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
        while (cursor.moveToNext()) {
            String email = cursor.getString(0);
            String message = cursor.getString(1);
            String timestamp = cursor.getString(2);
            Notification notification = new Notification(email,message, timestamp);
            notifications.add(notification);
        }
        cursor.close();
        return notifications;
    }



}
