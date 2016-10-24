package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import au.com.owenwalsh.capabilityconnect.Model.Attendance;
import au.com.owenwalsh.capabilityconnect.Model.TutorialWeekStudent;

/**
 * Created by Zikri on 13/10/2016.
 */

public class TutorialWeekStudentLogic {
    public static final String TAG = "TutWeekStudentLogic";
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;
    private Cursor cursor;

    public TutorialWeekStudentLogic(Context context) {
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

    public void addStudentToClass(String studentId, int tutorialId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.STUDENT_ID, studentId);
        contentValues.put(dbHelper.W_C_S_TUTORIAL_ID, tutorialId);
        long row = db.insert(dbHelper.CLASS_WEEK_STUDENT, null, contentValues);
        Log.d("Student Added to tut :", String.valueOf(row));
    }
    public long takeAttendance(Attendance attendance) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.ATTEND, attendance.getAttended());
        contentValues.put(dbHelper.W_C_S_WEEK_ID, attendance.getWeekId());
        long row =db.update(dbHelper.CLASS_WEEK_STUDENT,  contentValues, dbHelper.STUDENT_ID + " = '" + attendance.getStudentId() + "' ",null);
        return row;
    }

    public int getAttendance(String studentId) {
        int attended = 0;
        open();
        try {
            cursor = db.rawQuery("SELECT DISTINCT * FROM " + dbHelper.CLASS_WEEK_STUDENT + " WHERE " + dbHelper.STUDENT_ID + " = '" + studentId +"' AND " + dbHelper.ATTEND + " = " + 1, null);
            attended = cursor.getCount();
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            attended = 0;
        }
        cursor.close();
        close();
        return attended;
    }

}
