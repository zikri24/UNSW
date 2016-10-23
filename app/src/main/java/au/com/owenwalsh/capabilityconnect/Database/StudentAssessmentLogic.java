package au.com.owenwalsh.capabilityconnect.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Model.Assessment;
import au.com.owenwalsh.capabilityconnect.Model.StudentAssessment;

/**
 * Created by Zikri on 23/10/2016.
 */

public class StudentAssessmentLogic {
    public static final String TAG = "StudentAssessmentLogic";

    private ArrayList<StudentAssessment> studentAssessments;
    private StudentAssessment studentAssessment;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;
    private Cursor cursor;

    public StudentAssessmentLogic(Context context) {
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
    public StudentAssessment findAssessmentByStudentId(String studentId) {
        studentAssessment = new StudentAssessment();
        open();
        try {
            cursor = db.rawQuery("SELECT * FROM " + dbHelper.STUDENTS_ASSESSMENTS + " WHERE " + dbHelper.ASSESSMENTS_STUDENT_ID + " = '" + studentId + "' " , null);
            if (cursor != null) {
                cursor.moveToFirst();
                studentAssessment.setAssessmentName(cursor.getString(1));
                studentAssessment.setStudentMark(cursor.getString(2));
                studentAssessment.setMark(cursor.getInt(3));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            studentAssessment = null;
        }
        cursor.close();
        close();
        return studentAssessment;
    }
*/
}
