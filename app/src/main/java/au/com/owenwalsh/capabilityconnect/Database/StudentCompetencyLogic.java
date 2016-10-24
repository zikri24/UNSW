package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Model.StudentAssessment;
import au.com.owenwalsh.capabilityconnect.Model.StudentCompetency;

/**
 * Created by Zikri on 24/10/2016.
 */

public class StudentCompetencyLogic {
    public static final String TAG = "StudentCompetencyLogic";
    private ArrayList<StudentCompetency> studentCompetencies;
    private StudentCompetency studentCompetency;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;
    private Cursor cursor;

    public StudentCompetencyLogic(Context context) {
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

    public long insertRating(StudentCompetency studentCompetency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.COMPETENCY_STUDENT_ID, studentCompetency.getStudentId());
        contentValues.put(dbHelper.RATING, studentCompetency.getRating());
        contentValues.put(dbHelper.COMPETENCY_COMPETENCY_ID, studentCompetency.getCompetencyId());
        open();
        long row = db.insert(dbHelper.STUDENTS_COMPETENCIES, null, contentValues);
        close();
        return row;
    }
    public long updateRating(StudentCompetency studentCompetency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.COMPETENCY_STUDENT_ID, studentCompetency.getStudentId());
        contentValues.put(dbHelper.RATING, studentCompetency.getRating());
        contentValues.put(dbHelper.COMPETENCY_COMPETENCY_ID, studentCompetency.getCompetencyId());
        open();
        long row = db.update(dbHelper.STUDENTS_COMPETENCIES, contentValues, dbHelper.COMPETENCY_STUDENT_ID + "='" + studentCompetency.getStudentId()+"' AND " + dbHelper.COMPETENCY_COMPETENCY_ID + " = " + studentCompetency.getCompetencyId(), null);
        close();
        return row;
    }


    public int getRating(int competencyId, String studentId) {
        int rating= 0;
        open();
        try {
            cursor = db.rawQuery("SELECT " +  dbHelper.RATING +" FROM "
                    + dbHelper.STUDENTS_COMPETENCIES
                    + " WHERE " + dbHelper.COMPETENCY_STUDENT_ID  + " = '" + studentId +"'  AND " + dbHelper.COMPETENCY_COMPETENCY_ID + " = " +  competencyId , null);
            while (cursor.moveToNext()) {
                rating = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            rating = 0;
        }
        close();
        return rating;
    }

    public ArrayList<StudentCompetency> findAllStudentCompetencies(String studentId) {
        studentCompetencies = new ArrayList<>();
        open();
        try {
            cursor = db.rawQuery("SELECT " + dbHelper.ASSESSMENT_ID  +  ", " + dbHelper.ASSESSMENT_NAME + ", " + dbHelper.ASSESSMENT_MARK +" FROM "
                    + dbHelper.ASSESSMENTS  ,null);

            while (cursor.moveToNext()) {
                studentCompetency = new StudentCompetency();
                studentCompetency.setCompetencyId(cursor.getInt(0));
                studentCompetency.setCompName(cursor.getString(1));
                studentCompetency.setRating(getRating(cursor.getInt(0), studentId));
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











/*
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
*/
}
