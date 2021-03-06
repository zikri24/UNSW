package au.com.owenwalsh.capabilityconnect.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
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

import static au.com.owenwalsh.capabilityconnect.Database.DatabaseHelper.LAST_NAME;

public class StudentListActivity extends BaseActivity implements View.OnClickListener, StudentAdapter.ItemClickCallback {
    private static final String FIRST_NAME = "firstName";
    private static final String STU_ID = "stuID";
    private static final String LAST_NAME = "lastname";
    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private Boolean isFabOpen = false;
    private TextView emptyView;

    private StudentLogic studentLogic;
    private ArrayList<Student> students;
    private StudentAdapter adapter;
    private Student student;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  setContentView(R.layout.activity_student_list);
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_student_list, null, false);
        drawerLayout.addView(contentView, 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complete Student List");
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(StudentListActivity.this);
            }
        });



        initViews();

    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_student_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadStudents();
    }

    private void loadStudents() {
        //showProgressDialog();
        studentLogic = new StudentLogic(StudentListActivity.this);
        students = studentLogic.findAllStudent();
        if (students.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new StudentAdapter(students, StudentListActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setItemClickCallback(this);
            // hideProgressDialog();
        }
    }


    @Override
    public void onItemClick(int p) {
        student = students.get(p);
        Intent intent = new Intent(StudentListActivity.this, StudentViewDetailsActivity.class);
        intent.putExtra(STU_ID, student.getId());
        intent.putExtra(FIRST_NAME, student.getFirsName());
        intent.putExtra(LAST_NAME, student.getLastName());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int p) {
        student = students.get(p);
        new AlertDialog.Builder(StudentListActivity.this)
                .setTitle("Deleting " + student.getLastName())
                .setMessage("Are you sure you want to delete " + student.getLastName())
                .setIcon(R.drawable.warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        studentLogic.deleteStudent(student.getId());
                        Toast.makeText(StudentListActivity.this, "Student has been deleted ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudentListActivity.this, StudentListActivity.class);
                        startActivity(intent);
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


    /**
     * This method is a reusable progress dialog to alert the users that we are waitinf for data
     */
    /*private void showProgressDialog() {
        if (progress == null) {
            progress = ProgressDialog.show(getApplicationContext(), "loading...","wont be long!", true);
        }
    }
    /**
     * This method hides the progress dialog and resets it to null
     */
    /*private void hideProgressDialog() {
        if (progress != null && progress.isShowing()) {
            progress.hide();
            progress = null;
        }
    }
    */
    @Override
    public void onClick(View v) {

    }


}
