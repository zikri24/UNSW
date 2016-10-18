package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Model.Week;

/**
 * Created by Zikri on 13/10/2016.
 */

public class WeekLogic {
    public static final String TAG = "WeekLogic";

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    private ArrayList<Week> weeks;
    private Week week;

    public WeekLogic(Context context) {
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

    public long insertWeek(Week week) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.NAME, week.getName());
        long row = db.insert(dbHelper.WEEKS, null, contentValues);
        close();
        return row;

    }

    public long deleteWeek(int weekId ) {
        open();
        long row = db.delete(dbHelper.WEEKS, dbHelper.WEEK_ID + "= " + weekId , null);
        close();
        return row;
    }

    //FIND ALL TUTORIALS
    public ArrayList<Week> findAllWeeks() {
        weeks = new ArrayList<>();
        open();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.WEEKS, null);
            while (cursor.moveToNext()) {
                week = new Week();
                week.setId(cursor.getInt(0));
                week.setName(cursor.getString(1));
                weeks.add(week);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            weeks = null;
        }
        close();
        return weeks;
    }

}
