package au.com.owenwalsh.capabilityconnect.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Adapters.AttendanceAdapter;
import au.com.owenwalsh.capabilityconnect.Adapters.StudentAdapter;
import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;

public class StudentAttendanceActivity extends BaseActivity implements View.OnClickListener, AttendanceAdapter.ItemClickCallback {
    private static final String FIRST_NAME = "firstName";
    private static final String STU_ID = "stuID";
    private static final String LAST_NAME = "lastname";
    private static String TUT_ID = "tutorialId";

    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private Boolean isFabOpen = false;
    private TextView emptyView;
    private Button submitButton;

    private StudentLogic studentLogic;
    private ArrayList<Student> students;
    private AttendanceAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_student_attendance, null, false);
        drawerLayout.addView(contentView, 0);

        initViews();
        submitButton = (Button) findViewById(R.id.btn_submit_attendance);
        Intent intent = getIntent();
        final String tutorialId = intent.getStringExtra(TUT_ID);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loop here for attendance
            }
        });
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
        students = studentLogic.findAllStudent();
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
    public void onClick(View v) {

    }
}
