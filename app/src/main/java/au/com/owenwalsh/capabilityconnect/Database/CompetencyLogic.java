package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import au.com.owenwalsh.capabilityconnect.Model.Assessment;
import au.com.owenwalsh.capabilityconnect.Model.Competency;

/**
 * Created by Zikri on 13/10/2016.
 */

public class CompetencyLogic {
    public static final String TAG = "CompetencyLogic";

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    public CompetencyLogic(Context context) {
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

    public void insertCompetency(Competency competency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.COMPETENCY_NAME, competency.getName());
        contentValues.put(dbHelper.DESCRIPTION, competency.getDescription());
        long row = db.insert(dbHelper.STUDENTS_TABLE, null, contentValues);
        close();
        Log.d("Competency added ID IS:", String.valueOf(row));
    }


}
