package au.com.owenwalsh.capabilityconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Model.Student;

/**
 * Created by Zikri on 12/10/2016.
 */

public class StudentLogic {
    private Student student;
    private ArrayList<Student> students;
    private Cursor cursor;

    public static final String TAG = "StudentLogic";
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    public StudentLogic(Context context) {
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

    public long insertStudent(Student student, int tutorialId) {
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        contentValues.put(dbHelper.ZID, student.getId());
        contentValues.put(dbHelper.FIRST_NAME, student.getFirsName());
        contentValues.put(dbHelper.LAST_NAME, student.getLastName());
        contentValues.put(dbHelper.EMAIL, student.getEmail());
        contentValues.put(dbHelper.WEAKNESS, student.getWeakness());
        contentValues.put(dbHelper.STRENGTH, student.getStrength());
        contentValues.put(dbHelper.STREAM, student.getStream());
        contentValues2.put(dbHelper.STUDENT_ID, student.getId());
        contentValues2.put(dbHelper.W_C_S_TUTORIAL_ID, tutorialId);
        open();
        long row = db.insert(dbHelper.STUDENTS_TABLE, null, contentValues);
        long row2 = db.insert(dbHelper.CLASS_WEEK_STUDENT, null, contentValues2);
        close();
        return row;
    }


    public long updateStudent(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.ZID, student.getId());
        contentValues.put(dbHelper.FIRST_NAME, student.getFirsName());
        contentValues.put(dbHelper.LAST_NAME, student.getLastName());
        contentValues.put(dbHelper.EMAIL, student.getEmail());
        contentValues.put(dbHelper.STREAM, student.getStream());
        contentValues.put(dbHelper.WEAKNESS, student.getWeakness());
        contentValues.put(dbHelper.STRENGTH, student.getStrength());
        open();
        long row = db.update(dbHelper.STUDENTS_TABLE, contentValues, dbHelper.ZID + "='" + student.getId() +"' ", null);
        close();
        return row;
    }

    public Student findStudentById(String zID) {
        student = new Student();
        open();
        try {
            cursor = db.rawQuery("SELECT * FROM " + dbHelper.STUDENTS_TABLE + " WHERE " + dbHelper.ZID + "= '" + zID + "'", null);
            if (cursor != null) {
                cursor.moveToFirst();
                student.setId(cursor.getString(0));
                student.setFirsName(cursor.getString(1));
                student.setLastName(cursor.getString(2));
                student.setEmail(cursor.getString(3));
                student.setStream(cursor.getString(6));
                student.setWeakness(cursor.getString(5));
                student.setStrength(cursor.getString(4));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            student = null;
        }
        cursor.close();
        close();
        return student;
    }

    public ArrayList<Student> findAllStudent() {
        students = new ArrayList<>();
        open();
        try {
            cursor = db.rawQuery("SELECT * FROM " + dbHelper.STUDENTS_TABLE, null);
            while (cursor.moveToNext()) {
                student = new Student();
                student.setId(cursor.getString(0));
                student.setFirsName(cursor.getString(1));
                student.setLastName(cursor.getString(2));
                student.setEmail(cursor.getString(3));
                student.setWeakness(cursor.getString(4));
                student.setStrength(cursor.getString(5));
                students.add(student);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            students = null;
        }
        close();
        return students;
    }

    public ArrayList<Student> findStudentByClassId(int classId) {
        students = new ArrayList<>();
        open();
        try {
            cursor = db.rawQuery("SELECT DISTINCT * FROM " + dbHelper.STUDENTS_TABLE + " JOIN "
                    + dbHelper.CLASS_WEEK_STUDENT + " ON "
                    + dbHelper.STUDENTS_TABLE + "." + dbHelper.ZID + " = " + dbHelper.CLASS_WEEK_STUDENT + "." + dbHelper.STUDENT_ID
                    + " WHERE " + dbHelper.CLASS_WEEK_STUDENT + "." + dbHelper.TUTORIAL_ID + " = " + classId, null);
            while (cursor.moveToNext()) {
                student = new Student();
                student.setId(cursor.getString(0));
                student.setFirsName(cursor.getString(1));
                student.setLastName(cursor.getString(2));
                student.setEmail(cursor.getString(3));
                student.setWeakness(cursor.getString(4));
                student.setStrength(cursor.getString(5));
                students.add(student);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            students = null;
        }
        close();
        return students;
    }

    public long deleteStudent(String zID) {
        open();
        long row = db.delete(dbHelper.STUDENTS_TABLE, dbHelper.ZID + "= '" + zID + "'", null);
        close();
        return row;
    }
}
