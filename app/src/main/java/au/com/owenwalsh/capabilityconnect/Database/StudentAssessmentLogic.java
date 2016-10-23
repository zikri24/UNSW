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

    public long updateRating(StudentCompetency studentCompetency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.COMPETENCY_STUDENT_ID, studentCompetency.getStudentId());
        contentValues.put(dbHelper.RATING, studentCompetency.getRating());
        contentValues.put(dbHelper.COMPETENCY_COMPETENCY_ID, studentCompetency.getCompetencyId());
        open();
        long row = db.update(dbHelper.STUDENTS_COMPETENCIES, contentValues, dbHelper.COMPETENCY_STUDENT_ID + "='" + studentCompetency.getStudentId() + "' AND " + dbHelper.COMPETENCY_COMPETENCY_ID + " = " + studentCompetency.getCompetencyId(), null);
        close();
        return row;
    }


    public ArrayList<StudentCompetency> findCompetenciesByStudentId(String studentId) {
        studentCompetencies = new ArrayList<>();
        open();
        try {
            cursor = db.rawQuery("SELECT " + dbHelper.COMPETENCY_NAME + ", " + dbHelper.COMPETENCY_COMPETENCY_ID + ", " + dbHelper.RATING + " FROM "
                    + dbHelper.COMPETENCIES + " JOIN "
                    + dbHelper.STUDENTS_COMPETENCIES + " ON "
                    + dbHelper.COMPETENCIES + "." + dbHelper.COMPETENCY_ID + " = " + dbHelper.STUDENTS_COMPETENCIES + "." + dbHelper.COMPETENCY_COMPETENCY_ID + " JOIN "
                    + dbHelper.STUDENTS_TABLE + " ON "
                    + dbHelper.STUDENTS_COMPETENCIES + "." + dbHelper.COMPETENCY_STUDENT_ID + " = " + dbHelper.STUDENTS_TABLE + "." + dbHelper.ZID
                    + " WHERE " + dbHelper.STUDENTS_TABLE + "." + dbHelper.ZID + " = " + studentId, null);
            while (cursor.moveToNext()) {
                studentCompetency = new StudentCompetency();
                studentCompetency.setCompName(cursor.getString(0));
                studentCompetency.setCompetencyId(cursor.getInt(1));
                studentCompetency.setRating(cursor.getInt(2));
                studentCompetencies.add(studentCompetency);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            studentCompetencies = null;
        }
        close();
        return studentCompetencies;
    }
}
