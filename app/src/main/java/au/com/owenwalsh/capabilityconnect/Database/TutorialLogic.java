package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.Model.Tutorial;

/**
 * Created by Zikri on 12/10/2016.
 */

public class TutorialLogic {

    public static final String TAG = "TutorialLogic";

    private Tutorial tutorial;
    private ArrayList<Tutorial> tutorials;
    private Cursor cursor;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    public TutorialLogic(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addTutorial(Tutorial tutorial) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.DAY, tutorial.getDay());
        contentValues.put(dbHelper.TIME, tutorial.getTime());
        open();
        long row = db.insert(dbHelper.TUTORIALS, null, contentValues);
        close();
        return row;
    }

    public long deleteTutorial(int tutorialId ) {
        open();
        long row = db.delete(dbHelper.TUTORIALS, dbHelper.TUTORIAL_ID + "= " + tutorialId , null);
        close();
        return row;
    }

    public Tutorial findTutorialById(int id) {
        tutorial = new Tutorial();
        open();
        try {
            cursor = db.rawQuery("SELECT * FROM " + dbHelper.TUTORIALS + " WHERE " + dbHelper.TUTORIAL_ID + " = " + id , null);
            if (cursor != null) {
                cursor.moveToFirst();
                tutorial.setDay(cursor.getString(1));
                tutorial.setTime(cursor.getString(2));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            tutorial = null;
        }
        cursor.close();
        close();
        return tutorial;
    }

    //FIND ALL TUTORIALS
    public ArrayList<Tutorial> findAllTutorials() {
        tutorials = new ArrayList<>();
        open();
        try {
            Cursor tutCursor = db.rawQuery("SELECT * FROM " + dbHelper.TUTORIALS, null);
            while (tutCursor.moveToNext()) {
                tutorial = new Tutorial();
                tutorial.setNumberOfStudents(getStudentCount(tutCursor.getInt(0)));
                tutorial.setId(tutCursor.getInt(0));
                tutorial.setDay(tutCursor.getString(1));
                tutorial.setTime(tutCursor.getString(2));
                tutorials.add(tutorial);
            }
            tutCursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            tutorials = null;
        }
        close();
        return tutorials;
    }

    public int getStudentCount(int classId) {
        int count = 0;
        open();
        try {
            cursor = db.rawQuery("SELECT * FROM " + dbHelper.STUDENTS_TABLE + " JOIN "
                    + dbHelper.CLASS_WEEK_STUDENT + " ON "
                    + dbHelper.STUDENTS_TABLE + "." + dbHelper.ZID + " = " + dbHelper.CLASS_WEEK_STUDENT + "." + dbHelper.STUDENT_ID
                    + " WHERE " + dbHelper.CLASS_WEEK_STUDENT + "." + dbHelper.TUTORIAL_ID + " = " + classId, null);
            count  = cursor.getCount();
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        cursor.close();
        close();
        return count;
    }
}
