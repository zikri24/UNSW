package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import au.com.owenwalsh.capabilityconnect.Model.Attendance;

/**
 * Created by Zikri on 23/10/2016.
 */

public class AttendanceLogic {

    public static final String TAG = "TutWeekStudentLogic";
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    public AttendanceLogic(Context context) {
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
/*
    public long takeAttendance(Attendance attendance) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.ATTENDANCE_STUDENT_ID, attendance.getStudentId());
        contentValues.put(dbHelper.ATTENDANCE_WEEK_ID, attendance.getWeekId());
        contentValues.put(dbHelper.ATTENDED, attendance.getAttended());
        long row = db.insert(dbHelper.ATTENDANCES, null, contentValues);
        return row;
    }
    */
}
