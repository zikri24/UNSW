package au.com.owenwalsh.capabilityconnect.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Adapters.AttendanceAdapter;
import au.com.owenwalsh.capabilityconnect.Adapters.StudentAdapter;
import au.com.owenwalsh.capabilityconnect.Adapters.TutorialAdapter;
import au.com.owenwalsh.capabilityconnect.Database.AttendanceLogic;
import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Database.TutorialWeekStudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Attendance;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.Model.TutorialWeekStudent;
import au.com.owenwalsh.capabilityconnect.R;

//implements View.OnClickListener
public class StudentAttendanceActivity extends BaseActivity implements View.OnClickListener, AttendanceAdapter.ItemClickCallback {
    // private static final String STU_ID = "stuID";
    private static String TUT_ID = "tutorialId";
    private static String WEEK_ID = "weekId";


    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private TextView emptyView;
    private CheckBox attend;

    private StudentLogic studentLogic;
    private ArrayList<Student> students;
    private AttendanceAdapter adapter;
    private Button submitButton;
    private Attendance attendance;
    private TutorialWeekStudentLogic tutorialWeekStudentLogic;

    private String weekId;
    private String studentId;
    private String tutorialId;
    private int attended;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_student_attendance, null, false);
        drawerLayout.addView(contentView, 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student List");
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NavUtils.navigateUpFromSameTask(TutorialStudentsListActivity.this);
                Intent intent = new Intent(StudentAttendanceActivity.this, TutorialListActivity.class);
                intent.putExtra(tutorialId, tutorialId);
                 startActivity(intent);
            }
        });

        initViews();
        submitButton = (Button) findViewById(R.id.btn_submit_attendance);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TutorialListActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        weekId = intent.getStringExtra(WEEK_ID);
        //studentId = intent.getStringExtra(STU_ID);
        tutorialId = intent.getStringExtra(TUT_ID);


    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_student_attendance_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadStudents();
    }


    private void loadStudents() {
        //showProgressDialog();
        studentLogic = new StudentLogic(StudentAttendanceActivity.this);
        Intent intent = getIntent();
        final int tutId = Integer.parseInt(intent.getStringExtra(TUT_ID));
        students = studentLogic.findStudentByClassId(tutId);
        if (students.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new AttendanceAdapter(students, StudentAttendanceActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setItemClickCallback(this);
            // hideProgressDialog();

        }
    }

    @Override
    public void onItemClick(int p) {
        Student student = students.get(p);
        attend = (CheckBox) findViewById(R.id.checkbox_attendance);
        if (attend.isChecked()) {
            attended = 1;
        } else {
            attended = 0;
        }
        attendance = new Attendance(student.getId(), Integer.parseInt(weekId), attended);
        tutorialWeekStudentLogic = new TutorialWeekStudentLogic(this);
        tutorialWeekStudentLogic.takeAttendance(attendance);
    }

    @Override
    public void onClick(View view) {

    }
/*
    @Override
    public void onDeleteClick(int p) {
        /*Student student = students.get(p);
        studentLogic.deleteStudent(student.getId());
        new AlertDialog.Builder(StudentAttendanceActivity.this)
                .setTitle("Deleting " + student.getLastName())
                .setMessage("Are you sure you want to delete " +student.getLastName())
                .setIcon(R.drawable.warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(StudentAttendanceActivity.this, "Student has been deleted ", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onUpdateClick(int p) {
        /*Student student = students.get(p);
        Intent intent = new Intent(this, EditTutorialActivity.class);
        intent.putExtra(STU_ID, student.getId());
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {

    }
*/
}
