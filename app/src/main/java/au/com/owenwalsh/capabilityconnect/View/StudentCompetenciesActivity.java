package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Adapters.AttendanceAdapter;
import au.com.owenwalsh.capabilityconnect.Adapters.StudentCompetencyAdapter;
import au.com.owenwalsh.capabilityconnect.Database.CompetencyLogic;
import au.com.owenwalsh.capabilityconnect.Database.StudentCompetencyLogic;
import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.Model.StudentCompetency;
import au.com.owenwalsh.capabilityconnect.R;

public class StudentCompetenciesActivity extends BaseActivity implements View.OnClickListener, StudentCompetencyAdapter.ItemClickCallback {
    private static final String COMP_ID = "competencyID";
    private static final String STU_ID = "stuID";
    private static final String COMP_NAME = "competencyName";
    private RecyclerView recyclerView;
    private Button submitButton;
    private TextView emptyView;

    private StudentCompetencyLogic studentCompetencyLogic;
    private ArrayList<StudentCompetency> studentCompetencies;
    private StudentCompetency studentCompetency;
    private StudentCompetencyAdapter adapter;

    private String studentId;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_student_competencies, null, false);
        drawerLayout.addView(contentView, 0);


        Intent intent = getIntent();
        final String competencyID = intent.getStringExtra(COMP_ID);
        studentId = intent.getStringExtra(STU_ID);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Students Competencies");
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NavUtils.navigateUpFromSameTask(TutorialStudentsListActivity.this);
                Intent intent = new Intent(StudentCompetenciesActivity.this, StudentViewDetailsActivity.class);
                intent.putExtra(STU_ID, studentId);
                startActivity(intent);
            }
        });

        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_student_competencies_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadCompetencies();
    }

    private void loadCompetencies() {
        //showProgressDialog();
        studentCompetencyLogic = new StudentCompetencyLogic(StudentCompetenciesActivity.this);
        studentCompetencies = studentCompetencyLogic.findAllStudentCompetencies(studentId);
        if (studentCompetencies.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new StudentCompetencyAdapter(studentCompetencies, StudentCompetenciesActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setItemClickCallback(this);
            // hideProgressDialog();

        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(int p) {
        /*studentCompetency = studentCompetencies.get(p);
        Intent intent = new Intent(StudentCompetenciesActivity.this, AddStudentAssessmentMarkActivity.class);
        intent.putExtra(STU_ID, studentId);
        intent.putExtra(COMP_ID, String.valueOf(studentCompetency.getCompetencyId()));
        intent.putExtra(COMP_NAME, studentCompetency.getCompName());
        startActivity(intent);*/
    }

    @Override
    public void onEditClick(int p) {

    }

    @Override
    public void onAddClick(int p) {
        studentCompetency = studentCompetencies.get(p);
        Intent intent = new Intent(StudentCompetenciesActivity.this, AddStudentCompetencyRatingActivity.class);
        intent.putExtra(STU_ID, studentId);
        intent.putExtra(COMP_ID, String.valueOf(studentCompetency.getCompetencyId()));
        intent.putExtra(COMP_NAME, studentCompetency.getCompName());
        startActivity(intent);
    }
}
