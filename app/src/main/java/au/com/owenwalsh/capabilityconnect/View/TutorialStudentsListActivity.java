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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Adapters.StudentAdapter;
import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;

public class TutorialStudentsListActivity extends BaseActivity implements View.OnClickListener, StudentAdapter.ItemClickCallback {

    private static final String STU_ID = "stuID";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastname";
    private static final String tutorialId = "tutorialId";
    private static String TUT_ID = "tutorialId";
    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton addStudentActionBar;
    private FloatingActionButton viewTutorialDetailsActionBar;
    private Animation actionbar_open, actionbar_close, rotate_forward, rotate_backward;
    private TextView emptyView;

    private StudentLogic studentLogic;
    private ArrayList<Student> students;
    private Student student;
    private StudentAdapter adapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_tutorial_students_list, null, false);
        drawerLayout.addView(contentView, 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student List");
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(TutorialStudentsListActivity.this);
                //Intent intent = new Intent(StudentViewDetailsActivity.this, TutorialStudentsListActivity.class);
                //intent.putExtra(STU_ID, studentID);
                // startActivity(intent);
            }
        });

        initViews();

        addActionBar = (FloatingActionButton) findViewById(R.id.fab);
        addStudentActionBar = (FloatingActionButton) findViewById(R.id.fab1);
        viewTutorialDetailsActionBar = (FloatingActionButton) findViewById(R.id.fab2);
        actionbar_open = AnimationUtils.loadAnimation(TutorialStudentsListActivity.this, R.anim.actionbar_open);
        actionbar_close = AnimationUtils.loadAnimation(TutorialStudentsListActivity.this, R.anim.actionbar_close);
        rotate_forward = AnimationUtils.loadAnimation(TutorialStudentsListActivity.this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(TutorialStudentsListActivity.this, R.anim.rotate_backward);

        Intent intent = getIntent();
        final String tutorialId = intent.getStringExtra(TUT_ID);

        addActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        addStudentActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TutorialStudentsListActivity.this, "Add student selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Add student selected");
                //move user to AddStudentActivity
                Intent intent = new Intent(getApplicationContext(), AddStudentActivity.class);
                intent.putExtra(TUT_ID, tutorialId);
                startActivity(intent);
            }
        });
        viewTutorialDetailsActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TutorialStudentsListActivity.this, "Tutorial details selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Tutorial details selected");
                //move user to EmailStudentActivity
                Intent intent = new Intent(getApplicationContext(), TutorialViewDetailsActivity.class);
                intent.putExtra(TUT_ID, tutorialId);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_tutorial_student_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadStudents();
    }

    public void animateFAB() {

        if (isFabOpen) {

            addActionBar.startAnimation(rotate_backward);
            addStudentActionBar.startAnimation(actionbar_close);
            addStudentActionBar.setClickable(false);
            viewTutorialDetailsActionBar.startAnimation(actionbar_close);
            viewTutorialDetailsActionBar.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            addActionBar.startAnimation(rotate_forward);
            addStudentActionBar.startAnimation(actionbar_open);
            addStudentActionBar.setClickable(true);
            viewTutorialDetailsActionBar.startAnimation(actionbar_open);
            viewTutorialDetailsActionBar.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");
        }
    }

    private void loadStudents() {
        //showProgressDialog();
        studentLogic = new StudentLogic(TutorialStudentsListActivity.this);
        Intent intent = getIntent();
        final int tutId = Integer.parseInt(intent.getStringExtra(TUT_ID));
        students = studentLogic.findStudentByClassId(tutId);
        if (students.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new StudentAdapter(students, TutorialStudentsListActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setItemClickCallback(this);
            // hideProgressDialog();
        }
    }

    @Override
    public void onItemClick(int p) {
        student = students.get(p);
        Intent intent = new Intent(TutorialStudentsListActivity.this, StudentViewDetailsActivity.class);
        intent.putExtra(STU_ID, student.getId());
        intent.putExtra(TUT_ID, tutorialId);
        intent.putExtra(TUT_ID, tutorialId);
        intent.putExtra(FIRST_NAME, student.getFirsName());
        intent.putExtra(LAST_NAME, student.getLastName());
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int p) {
        student = students.get(p);
        new AlertDialog.Builder(TutorialStudentsListActivity.this)
                .setTitle("Deleting " + student.getLastName())
                .setMessage("Are you sure you want to delete " + student.getLastName())
                .setIcon(R.drawable.warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        studentLogic.deleteStudent(student.getId());
                        Toast.makeText(TutorialStudentsListActivity.this, "Student has been deleted ", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onUpdateClick(int p) {
        student = students.get(p);
        Intent intent = new Intent(this, EditStudentActivity.class);
        intent.putExtra(STU_ID, student.getId());
        startActivity(intent);
    }

    public void hideFloatingActionBar() {
        addStudentActionBar.startAnimation(actionbar_close);
        addStudentActionBar.setClickable(false);
        addStudentActionBar.hide();
        addActionBar.hide();
    }

    @Override
    public void onClick(View v) {

    }
}
