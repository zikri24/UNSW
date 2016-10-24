package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
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

    public long insertAssessmentMark(StudentAssessment studentAssessment) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.ASSESSMENTS_STUDENT_ID, studentAssessment.getStudentId());
        contentValues.put(dbHelper.ASSESSMENTS_ASSESSMENTS_ID, studentAssessment.getAssessmentId());
        contentValues.put(dbHelper.STUDENT_MARK, studentAssessment.getStudentMark());
        open();
        long row = db.insert(dbHelper.STUDENTS_ASSESSMENTS, null, contentValues);
        close();
        return row;
    }

    public long updateRating(StudentAssessment studentAssessment) {        ContentValues contentValues = new ContentValues();

        contentValues.put(dbHelper.STUDENT_MARK, studentAssessment.getStudentMark());
        open();
        long row = db.update(dbHelper.STUDENTS_ASSESSMENTS, contentValues, dbHelper.ASSESSMENTS_STUDENT_ID + "='" + studentAssessment.getStudentId() + "' AND " + dbHelper.ASSESSMENTS_ASSESSMENTS_ID + " = " + studentAssessment.getAssessmentId(), null);
        close();
        return row;
    }


    public ArrayList<StudentAssessment> findAssessmentsByStudentId(String studentId) {
        studentAssessments = new ArrayList<>();
        open();
        try {
            cursor = db.rawQuery("SELECT " + dbHelper.ASSESSMENT_NAME + ", " + dbHelper.ASSESSMENT_MARK + ", " + dbHelper.ASSESSMENT_ID +", " + dbHelper.STUDENT_MARK +" FROM "
                    + dbHelper.ASSESSMENTS + " JOIN "
                    + dbHelper.STUDENTS_ASSESSMENTS + " ON "
                    + dbHelper.ASSESSMENTS + "." + dbHelper.ASSESSMENT_ID + " = " + dbHelper.STUDENTS_ASSESSMENTS + "." + dbHelper.ASSESSMENTS_ASSESSMENTS_ID + " JOIN "
                    + dbHelper.STUDENTS_TABLE + " ON "
                    + dbHelper.STUDENTS_ASSESSMENTS + "." + dbHelper.ASSESSMENTS_STUDENT_ID + " = " + dbHelper.STUDENTS_TABLE + "." + dbHelper.ZID
                    + " WHERE " + dbHelper.STUDENTS_TABLE + "." + dbHelper.ZID + " = " + studentId, null);
            while (cursor.moveToNext()) {
                studentAssessment = new StudentAssessment();
                studentAssessment.setAssessmentName(cursor.getString(0));
                studentAssessment.setAssessmentMark(cursor.getInt(1));
                studentAssessment.setAssessmentId(cursor.getInt(2));
                studentAssessment.setStudentMark(cursor.getDouble(3));
                studentAssessments.add(studentAssessment);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            studentAssessments = null;
        }
        close();
        return studentAssessments;
    }
}
