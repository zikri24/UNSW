package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Adapters.AttendanceAdapter;
import au.com.owenwalsh.capabilityconnect.Adapters.StudentCompetencyAdapter;
import au.com.owenwalsh.capabilityconnect.Database.CompetencyLogic;
import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.R;

public class StudentCompetenciesActivity extends BaseActivity implements View.OnClickListener, StudentCompetencyAdapter.ItemClickCallback {
    private static final String COMP_ID = "competencyID";
    private static final String STU_ID = "stuID";
    private RecyclerView recyclerView;
    private Button submitButton;
    private TextView emptyView;

    private CompetencyLogic competencyLogic;
    private ArrayList<Competency> competencies;
    private StudentCompetencyAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_student_competencies, null, false);
        drawerLayout.addView(contentView, 0);

        initViews();
        submitButton = (Button) findViewById(R.id.btn_submit_competencies);
        Intent intent = getIntent();
        final String competencyID = intent.getStringExtra(COMP_ID);
        final String stuID = intent.getStringExtra(STU_ID);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loop through to get and submit competencies
            }
        });
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_student_attendance_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadCompetencies();
    }

    private void loadCompetencies() {
        //showProgressDialog();
        competencyLogic = new CompetencyLogic(StudentCompetenciesActivity.this);
        competencies = competencyLogic.findAllCompetencies();
        if (competencies.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new StudentCompetencyAdapter(competencies, StudentCompetenciesActivity.this);
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
