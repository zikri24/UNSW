package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Model.Assessment;

/**
 * Created by Zikri on 13/10/2016.
 */

public class AssessmentLogic {

    public static final String TAG = "AssessmentLogic";

    private ArrayList<Assessment> assessments;
    private Assessment assessment;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    public AssessmentLogic(Context context) {
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

    public long insertAssessment(Assessment assessment) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.ASSESSMENT_NAME, assessment.getName());
        contentValues.put(dbHelper.ASSESSMENT_MARK, assessment.getMark());
        contentValues.put(dbHelper.DUE_DATE, assessment.getDueDate());
        long row = db.insert(dbHelper.STUDENTS_TABLE, null, contentValues);
        close();
        return row;
    }

    public long deleteAssessment(int assessmentId ) {
        open();
        long row = db.delete(dbHelper.ASSESSMENTS, dbHelper.ASSESSMENT_ID + "= " + assessmentId , null);
        close();
        return row;
    }

    //FIND ALL Assessments
    public ArrayList<Assessment> findAllAssessments() {
        assessments = new ArrayList<>();
        open();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.ASSESSMENTS, null);
            while (cursor.moveToNext()) {
                assessment = new Assessment();
                assessment.setId(cursor.getInt(0));
                assessment.setName(cursor.getString(1));
                assessment.setDueDate(cursor.getString(2));
                assessment.setMark(cursor.getInt(3));
                assessments.add(assessment);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            assessments = null;
        }
        close();
        return assessments;
    }


}
