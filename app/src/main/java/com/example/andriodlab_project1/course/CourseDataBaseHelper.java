package com.example.andriodlab_project1.course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.example.andriodlab_project1.common.DataBaseHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CourseDataBaseHelper {
    private static DataBaseHelper dbHelper;
    private static int databaseVersion = 1;
    private byte[] imageBytes;
    private ByteArrayOutputStream objectByteArrayOutputStream;

    public CourseDataBaseHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        if (isTableCreatedFirstTime("COURSE")) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            // Create the table
            sqLiteDatabase.execSQL("CREATE TABLE COURSE(COURSE_ID INTEGER PRIMARY KEY AUTOINCREMENT, Course_Title TEXT, Course_Main_Topics TEXT, " +
                    "Prerequisites TEXT NOT NULL,Photo BLOB)");
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
    public boolean insertCourse(Course course) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM COURSE WHERE COURSE_ID  = \"" + course.getCourseID() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Course_Title",course.getCourseTitle());
            StringBuilder stringBuilder = new StringBuilder();
            for (String topic : course.getCourseMainTopics()) {
                stringBuilder.append(topic).append(",");
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1); // Remove the last comma
            }
            contentValues.put("Course_Main_Topics", stringBuilder.toString());

            StringBuilder stringBuilderForPre = new StringBuilder();
            for (String pre : course.getPrerequisites()) {
                stringBuilderForPre.append(pre).append(",");
            }
            if (stringBuilderForPre.length() > 0) {
                stringBuilderForPre.deleteCharAt(stringBuilderForPre.length() - 1); // Remove the last comma
            }
            contentValues.put("Prerequisites", stringBuilderForPre.toString());
            Bitmap imageStore = course.getPhoto();
            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageStore.compress(Bitmap.CompressFormat.PNG,100,objectByteArrayOutputStream);
            imageBytes = objectByteArrayOutputStream.toByteArray();
            contentValues.put("Photo",imageBytes);

            //Photo
            sqLiteDatabase.insert("COURSE", null, contentValues);
            return true;
        }
        return false;
    }
    public boolean deleteCourse(int courseId){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int rowsAffected=sqLiteDatabase.delete("COURSE", "COURSE_ID = ?", new String[]{String.valueOf(courseId)});
        sqLiteDatabase.close();
        if (rowsAffected > 0) {
            return true;
            //toastMessage
            // Update was successful
            // You can perform any additional actions or show a success message
        } else {
            return false;
            //toastMessage
            // Update failed
            // Handle the case where the course with the given courseId doesn't exist or other errors occurred
        }
    }
    public Boolean updateCourse(Course course) {
        SQLiteDatabase sqLiteDatabaseR = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM COURSE WHERE COURSE_ID = ?", new String[]{String.valueOf(course.getCourseID())});

        if (cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Course_Title", course.getCourseTitle());
            StringBuilder stringBuilder = new StringBuilder();
            for (String topic : course.getCourseMainTopics()) {
                stringBuilder.append(topic).append(",");
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1); // Remove the last comma
            }
            contentValues.put("Course_Main_Topics", stringBuilder.toString());
            StringBuilder stringBuilderForPre = new StringBuilder();
            for (String pre : course.getPrerequisites()) {
                stringBuilderForPre.append(pre).append(",");
            }
            if (stringBuilderForPre.length() > 0) {
                stringBuilderForPre.deleteCharAt(stringBuilderForPre.length() - 1); // Remove the last comma
            }
            contentValues.put("Prerequisites", stringBuilderForPre.toString());

            //Photo
            int rowsAffected = sqLiteDatabase.update("COURSE", contentValues, "COURSE_ID = ?", new String[]{String.valueOf(course.getCourseID())});
            sqLiteDatabase.close();
            if (rowsAffected > 0) {
                return true;
                //toastMessage
                // Update was successful
                // You can perform any additional actions or show a success message
            } else {
                return false;
                //toastMessage
                // Update failed
                // Handle the case where the course with the given courseId doesn't exist or other errors occurred
            }
        }
        return false;
    }
    public static boolean isCourseExists(int courseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM COURSE WHERE COURSE_ID = \"" + courseId + "\";", null);
        boolean isCourseExists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return isCourseExists;
    }

    public String getCourseName(int courseId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT c.Course_Title FROM COURSE c WHERE COURSE_ID = ?", new String[]{String.valueOf(courseId)});
        if (cursor.moveToFirst()) {
            String title = cursor.getString(0);
            return title;
        }
        return null;
    }
    public List<Map.Entry<String, String>>  getAllCourses() {
        List<Map.Entry<String, String>> courses = new ArrayList<>();

        SQLiteDatabase db  = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT c.COURSE_ID,c.Course_Title FROM COURSE c",null);

        if (cursor.moveToFirst()) {
            do {
                courses.add(new AbstractMap.SimpleEntry<>(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return courses;
    }
    public Course getCourseByID(int courseID) {
        SQLiteDatabase db  = dbHelper.getReadableDatabase();
        // Retrieve the course details from the database using the given course ID
        Cursor cursor = db.rawQuery("SELECT * FROM COURSE WHERE COURSE_ID = ?", new String[]{String.valueOf(courseID)});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String mainTopics = cursor.getString(2);
            String prerequisites = cursor.getString(3);

            // Create and return a Course object with the retrieved attributes
            Course course=new Course();
            course.setCourseTitle(title);
            course.setCourseID(id);
            course.setCourseMainTopics(convertStringToList(mainTopics));
            course.setPrerequisites(convertStringToList(prerequisites));
            return course;
        }
        // If no course is found with the given ID, return null
        return null;
    }
    public ArrayList<String> convertStringToList(String input) {
            String trimmedInput = input.trim();
            if (trimmedInput.endsWith(",")) {
                trimmedInput = trimmedInput.substring(0, trimmedInput.length() - 1);
            }
            String[] splitArray = trimmedInput.split(",");
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(splitArray));
            return arrayList;
    }

    public Bitmap getImage(String course_title) {
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT Photo FROM COURSE WHERE Course_Title = ?", new String[]{course_title});
        Bitmap ret = null;
        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                byte[] conv = c.getBlob(0);
                ret = BitmapFactory.decodeByteArray(conv, 0, conv.length);
            }
        }
        return ret;
    }



    public Bitmap getCourseImage(String courseT) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("COURSE", new String[]{"Photo"}, "Course_Title=?", new String[]{courseT}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        } else {
            cursor.close();
            throw new IllegalArgumentException("No record found with Course_Title = " + courseT);
        }

        int photoColumnIndex = cursor.getColumnIndex("Photo");
        if (photoColumnIndex == -1) {
            cursor.close();
            throw new IllegalArgumentException("Column 'Photo' does not exist");
        }

        byte[] photo = cursor.getBlob(photoColumnIndex);
        cursor.close();

        if (photo != null) {
            // Use BitmapFactory.Options to reduce the resolution if necessary
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(photo, 0, photo.length, options);

            // Calculate inSampleSize based on the target size
            int targetWidth = 1024; // adjust these values based on your requirements
            int targetHeight = 768;
            options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length, options);

            if (bitmap != null) {
                return bitmap;
            } else {
                // Handle the case when decoding fails (e.g., return a placeholder image)
                return null;
            }
        } else {
            throw new IllegalArgumentException("No photo found for Course_Title = " + courseT);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
