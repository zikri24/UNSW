package au.com.owenwalsh.capabilityconnect.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Zikri on 13/10/2016.
 */

public class WeekLogic {
    public static final String TAG = "WeekLogic";

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

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

}
