package au.com.owenwalsh.capabilityconnect.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by owenw on 11/10/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Database name and version
    private static final String DATABASE_NAME = "StudentsDB.db";
    private static final int DATABASE_VERSION = 1;

    //Students Table and columns names
    public final static String STUDENTS_TABLE = "Students";
    public final static String ZID = "zID";
    public final static String FIRST_NAME = "FirstName";
    public final static String LAST_NAME = "LastName";
    public final static String EMAIL = "Email";
    public final static String WEAKNESS = "Weakness";
    public final static String STRENGTH = "Strength";
    public final static String STREAM = "Stream";

    //Tutorials table and columns names
    public final static String TUTORIALS = "Tutorials";
    public final static String TUTORIAL_ID = "TutorialId";
    public final static String DAY = "Day";
    public final static String TIME = "Time";

    //Weeks table and columns names
    public final static String WEEKS = "Weeks";
    public final static String WEEK_ID = "WeekId";
    public final static String NAME = "Name";
    public final static String WEEK_COMMENT = "WeekComment";
    public final static String TO_DO_NEXT_WEEK = "ToDoNextWeek";

    //CLASS_WEEK_STUDENT table and columns names
    public final static String CLASS_WEEK_STUDENT = "Class_Week_Student";
    public final static String STUDENT_ID = "StudentId";
    public final static String W_C_S_WEEK_ID = "WeekId";
    public final static String W_C_S_TUTORIAL_ID = "TutorialId";
    public final static String ATTEND = "Attend";

    //Assessments table and columns names
    public final static String ASSESSMENTS = "Assessments";
    public final static String ASSESSMENT_ID = "AssessmentId";
    public final static String ASSESSMENT_NAME = "AssessmentName";
    public final static String ASSESSMENT_MARK = "AssessmentMark";
    public final static String DUE_DATE = "DueDate";


    //Assessments_Students table and columns names
    public final static String STUDENTS_ASSESSMENTS = "Students_Assessments";
    public final static String ASSESSMENTS_STUDENT_ID = "AssessmentStudentId";
    public final static String ASSESSMENTS_ASSESSMENTS_ID = "AssessmentAssessmentsId";
    public final static String STUDENT_MARK = "StudentMark";
    public final static String ASSESSMENT_COMMENT = "AssessmentComment";

    //COMPETENCIES table and columns names
    public final static String COMPETENCIES = "Competencies";
    public final static String COMPETENCY_ID = "CompetencyId";
    public final static String COMPETENCY_NAME = "CompetencyName";
    public final static String DESCRIPTION = "Description";

    //Students_Competencies table and columns names
    public final static String STUDENTS_COMPETENCIES = "Students_Competencies";
    public final static String COMPETENCY_STUDENT_ID = "CompetencyStudentId";
    public final static String COMPETENCY_COMPETENCY_ID = "CompetencyCompetencyId";
    public final static String RATING = "Rating";
/*
    //Notes table and columns names
    public final static String NOTES = "Notes";
    public final static String COMPETENCY_STUDENT_ID = "CompetencyStudentId";
    public final static String COMPETENCY_COMPETENCY_ID = "CompetencyCompetencyId";
    public final static String RATING = "Rating";

*/
    //Creating the Students table
    private static final String CREATE_STUDENTS_TABLE = "CREATE TABLE "
            + STUDENTS_TABLE + " (" + ZID + " TEXT PRIMARY KEY, "
            + FIRST_NAME + " TEXT NOT NULL, "
            + LAST_NAME + " TEXT NOT NULL, "
            + EMAIL + " TEXT NOT NULL, "
            + WEAKNESS + " TEXT, "
            + STRENGTH + " TEXT, "
            + STREAM+ " TEXT )";

    //Creating the Tutorials table
    private static final String CREATE_TUTORIALS_TABLE = "CREATE TABLE "
            + TUTORIALS + " (" + TUTORIAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DAY + " TEXT NOT NULL, "
            + TIME + " TEXT NOT NULL)";

    //Creating the WEEKS  table
    private static final String CREATE_WEEKS_TABLE = "CREATE TABLE "
            + WEEKS + " (" + WEEK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL, "
            + TO_DO_NEXT_WEEK + " TEXT, "
            + WEEK_COMMENT + " TEXT)";

    //Creating the CLASS_WEEK_STUDENT  table
    private static final String CREATE_CLASS_WEEK_STUDENT_TABLE = "CREATE TABLE "
            + CLASS_WEEK_STUDENT + " (" + ATTEND + " INTEGER, "
            + STUDENT_ID + " TEXT, "
            + W_C_S_TUTORIAL_ID + " INTEGER, "
            + W_C_S_WEEK_ID + " INTEGER, "
            + "FOREIGN KEY(" + STUDENT_ID + ") REFERENCES " + STUDENTS_TABLE + "(" + ZID + "),"
            + "FOREIGN KEY(" + W_C_S_TUTORIAL_ID + ") REFERENCES " + TUTORIALS + "(" + TUTORIAL_ID + "),"
            + "FOREIGN KEY(" + W_C_S_WEEK_ID + ") REFERENCES " + WEEKS + "(" + WEEK_ID + "))";

    //Creating the Competency table
    private static final String CREATE_COMPETENCY_TABLE = "CREATE TABLE "
            + COMPETENCIES + " (" + COMPETENCY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COMPETENCY_NAME + " TEXT, "
            + DESCRIPTION + " TEXT)";

    //Creating the Competency_Students table
    private static final String CREATE_STUDENTS_COMPETENCIES_TABLE = "CREATE TABLE "
            + STUDENTS_COMPETENCIES + " (" + RATING + " INTEGER, "
            + COMPETENCY_STUDENT_ID + " TEXT, "
            + COMPETENCY_COMPETENCY_ID + " INTEGER, "
            + "FOREIGN KEY(" + COMPETENCY_STUDENT_ID + ") REFERENCES " + STUDENTS_TABLE + "(" + ZID + "),"
            + "FOREIGN KEY(" + COMPETENCY_COMPETENCY_ID + ") REFERENCES " + COMPETENCIES + "(" + COMPETENCY_ID + "))";

    //Creating the Assessments table
    private static final String CREATE_ASSESSMENTS_TABLE = "CREATE TABLE "
            + ASSESSMENTS + " (" + ASSESSMENT_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ASSESSMENT_NAME + " TEXT, "
            + DUE_DATE + " TEXT, "
            + ASSESSMENT_MARK + " INTEGER)";

    //Creating the Students_Assessments table
    private static final String CREATE_STUDENTS_ASSESSMENTS_TABLE = "CREATE TABLE "
            + STUDENTS_ASSESSMENTS + " (" + STUDENT_MARK + " INTEGER, "
            + ASSESSMENTS_STUDENT_ID + " TEXT, "
            + ASSESSMENTS_ASSESSMENTS_ID + " INTEGER, "
            + "FOREIGN KEY(" + ASSESSMENTS_STUDENT_ID + ") REFERENCES " + STUDENTS_TABLE + "(" + ZID + "),"
            + "FOREIGN KEY(" + ASSESSMENTS_ASSESSMENTS_ID + ") REFERENCES " + ASSESSMENTS + "(" + ASSESSMENT_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_STUDENTS_TABLE);
        database.execSQL(CREATE_TUTORIALS_TABLE);
        database.execSQL(CREATE_WEEKS_TABLE);
        database.execSQL(CREATE_CLASS_WEEK_STUDENT_TABLE);
        database.execSQL(CREATE_COMPETENCY_TABLE);
        database.execSQL(CREATE_STUDENTS_COMPETENCIES_TABLE);
        database.execSQL(CREATE_ASSESSMENTS_TABLE);
        database.execSQL(CREATE_STUDENTS_ASSESSMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + CREATE_STUDENTS_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + CREATE_TUTORIALS_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + CREATE_WEEKS_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + CREATE_CLASS_WEEK_STUDENT_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + CREATE_COMPETENCY_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + CREATE_STUDENTS_COMPETENCIES_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + CREATE_ASSESSMENTS_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + CREATE_STUDENTS_ASSESSMENTS_TABLE);
        onCreate(database);
    }

}
