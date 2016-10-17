package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import au.com.owenwalsh.capabilityconnect.Model.Assessment;
import au.com.owenwalsh.capabilityconnect.Model.Student;

/**
 * Created by Zikri on 13/10/2016.
 */

public class AssessmentLogic {

    public static final String TAG = "AssessmentLogic";

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

    public void insertAssessment(Assessment assessment) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.ASSESSMENT_NAME, assessment.getName());
        contentValues.put(dbHelper.ASSESSMENT_MARK, assessment.getMark());
        contentValues.put(dbHelper.DUE_DAY, assessment.getDueDay());
        contentValues.put(dbHelper.DUE_MONTH, assessment.getDueMonth());
        contentValues.put(dbHelper.DUE_Year, assessment.getDueYear());
        long row = db.insert(dbHelper.STUDENTS_TABLE, null, contentValues);
        close();
        Log.d("Assessment added :", String.valueOf(row));
    }


}
