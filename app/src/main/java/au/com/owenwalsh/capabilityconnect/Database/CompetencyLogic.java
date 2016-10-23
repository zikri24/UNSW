package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Model.Assessment;
import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.Model.Tutorial;

/**
 * Created by Zikri on 13/10/2016.
 */

public class CompetencyLogic {
    public static final String TAG = "CompetencyLogic";

    private ArrayList<Competency> competencies;
    private Competency competency;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;
    private Cursor cursor;

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

    public long insertCompetency(Competency competency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.COMPETENCY_NAME, competency.getName());
        contentValues.put(dbHelper.DESCRIPTION, competency.getDescription());
        long row = db.insert(dbHelper.COMPETENCIES, null, contentValues);
        close();
       return row;
    }

    public long deleteCompetency(int competencyId ) {
        open();
        long row = db.delete(dbHelper.COMPETENCIES, dbHelper.COMPETENCY_ID + "= " + competencyId , null);
        close();
        return row;
    }

    //FIND ALL TUTORIALS
    public ArrayList<Competency> findAllCompetencies() {
        competencies = new ArrayList<>();
        open();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.COMPETENCIES, null);
            while (cursor.moveToNext()) {
                competency = new Competency();
                competency.setId(cursor.getInt(0));
                competency.setName(cursor.getString(1));
                competency.setDescription(cursor.getString(2));
                competencies.add(competency);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            competencies = null;
        }
        close();
        return competencies;
    }

    public Competency findCompetencyById(int competencyId) {
        competency = new Competency();
        open();
        try {
            cursor = db.rawQuery("SELECT * FROM " + dbHelper.COMPETENCIES + " WHERE " + dbHelper.COMPETENCY_ID + " = " + competencyId , null);
            if (cursor != null) {
                cursor.moveToFirst();
                competency.setName(cursor.getString(1));
                competency.setDescription(cursor.getString(2));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            competency = null;
        }
        cursor.close();
        close();
        return competency;
    }


}
