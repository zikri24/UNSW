package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Zikri on 13/10/2016.
 */

public class TutorialWeekStudentLogic {
    public static final String TAG = "TutWeekStudentLogic";
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

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
}
