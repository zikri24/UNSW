package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Model.Note;

/**
 * Created by Zikri on 23/10/2016.
 */

public class NotesLogic {


    public static final String TAG = "NotesLogic";

    private ArrayList<Note> notes;
    private Note note;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;
    private Cursor cursor;

    public NotesLogic(Context context) {
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

    public long insertNote(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.NOTE, note.getContent());
        contentValues.put(dbHelper.NOTE_STUDENT_ID, note.getStudentId());
        long row = db.insert(dbHelper.NOTES, null, contentValues);
        close();
        return row;
    }

    public long deleteNote(int noteId) {
        open();
        long row = db.delete(dbHelper.NOTES, dbHelper.NOTE_ID + "= " + noteId, null);
        close();
        return row;
    }

    //FIND ALL Notes
    public ArrayList<Note> findNotesByStudentId(String stuentId) {
        notes = new ArrayList<>();
        open();
        try {
            cursor = db.rawQuery("SELECT * FROM " + dbHelper.NOTES + " WHERE " + dbHelper.NOTE_STUDENT_ID + "= '" + stuentId + "'", null);
            while (cursor.moveToNext()) {
                note = new Note();
                note.setNoteId(cursor.getInt(0));
                note.setContent(cursor.getString(1));
                notes.add(note);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            notes = null;
        }
        close();
        return notes;
    }

}
