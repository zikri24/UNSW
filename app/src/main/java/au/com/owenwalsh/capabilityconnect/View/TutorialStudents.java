package au.com.owenwalsh.capabilityconnect.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Adapters.StudentAdapter;
import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;

public class TutorialStudents extends AppCompatActivity implements View.OnClickListener, StudentAdapter.ItemClickCallback {

    public static final String FIRST_NAME = "fistName";
    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton addStudentActionBar;
    private Animation actionbar_open,actionbar_close,rotate_forward,rotate_backward;

    private StudentLogic studentLogic;
    private ArrayList<Student> students;
    private StudentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_students);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_student_list, null, false);
        //drawerLayout.addView(contentView, 0);

        initViews();

        addActionBar = (FloatingActionButton) findViewById(R.id.fab);
        addStudentActionBar = (FloatingActionButton) findViewById(R.id.fab1);
        actionbar_open = AnimationUtils.loadAnimation(TutorialStudents.this, R.anim.actionbar_open);
        actionbar_close = AnimationUtils.loadAnimation(TutorialStudents.this,R.anim.actionbar_close);
        rotate_forward = AnimationUtils.loadAnimation(TutorialStudents.this,R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(TutorialStudents.this,R.anim.rotate_backward);

        addActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        addStudentActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TutorialStudents.this, "Add student selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Add student selected");
                //move user to AddStudentActivity
                Intent intent = new Intent(getApplicationContext(), AddStudentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_student_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadStudents();
    }

    public void animateFAB(){

        if(isFabOpen){

            addActionBar.startAnimation(rotate_backward);
            addStudentActionBar.startAnimation(actionbar_close);
            addStudentActionBar.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            addActionBar.startAnimation(rotate_forward);
            addStudentActionBar.startAnimation(actionbar_open);
            addStudentActionBar.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");
        }
    }

    private void loadStudents() {
        //showProgressDialog();
        studentLogic = new StudentLogic(TutorialStudents.this);
        Intent intent = getIntent();
        final int tutId = Integer.parseInt(intent.getStringExtra(TutorialListActivity.TUT_ID));
        students = studentLogic.findStudentByClassId(tutId);
        adapter = new StudentAdapter(students, TutorialStudents.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setItemClickCallback(this);
        // hideProgressDialog();
    }

    @Override
    public void onItemClick(int p) {
        Student student = students.get(p);
        Intent intent = new Intent(TutorialStudents.this, DummyActivity.class);
        intent.putExtra(FIRST_NAME, student.getFirsName());
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int p) {
        Student student = students.get(p);
        studentLogic.deleteStudent(student.getId());
        new AlertDialog.Builder(TutorialStudents.this)
                .setTitle("Deleting " + student.getLastName())
                .setMessage("Are you sure you want to delete " +student.getLastName())
                .setIcon(R.drawable.warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(TutorialStudents.this, "Student has been deleted ", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onUpdateClick(int p) {
        Student student = students.get(p);
        Intent intent = new Intent(this, DummyActivity.class);
        intent.putExtra(FIRST_NAME, student.getFirsName());
        startActivity(intent);
    }

    public void hideFloatingActionBar(){
        addStudentActionBar.startAnimation(actionbar_close);
        addStudentActionBar.setClickable(false);
        addStudentActionBar.hide();
        addActionBar.hide();
    }

    @Override
    public void onClick(View v) {

    }
}
