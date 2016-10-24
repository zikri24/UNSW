package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Adapters.StudentAssessmentAdapter;
import au.com.owenwalsh.capabilityconnect.Database.AssessmentLogic;
import au.com.owenwalsh.capabilityconnect.Database.StudentAssessmentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Assessment;
import au.com.owenwalsh.capabilityconnect.Model.StudentAssessment;
import au.com.owenwalsh.capabilityconnect.R;

public class StudentAssessmentActivity extends BaseActivity implements View.OnClickListener, StudentAssessmentAdapter.ItemClickCallback {
    private static final String STU_ID = "stuID";
    private static final String ASS_ID = "assID";
    private RecyclerView recyclerView;
    private TextView emptyView;

    private StudentAssessmentLogic studentAssessmentLogic;
    private ArrayList<StudentAssessment> studentAssessments;
    private StudentAssessmentAdapter adapter;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_student_assessment, null, false);
        drawerLayout.addView(contentView, 0);

        Intent intent = getIntent();
        final String assID = intent.getStringExtra(ASS_ID);
        studentId = intent.getStringExtra(STU_ID);

        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_student_assessments_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadAssessments();
    }

    private void loadAssessments() {
        //showProgressDialog();
        studentAssessmentLogic = new StudentAssessmentLogic(StudentAssessmentActivity.this);
        studentAssessments = studentAssessmentLogic.findAllAssessments(studentId);
        if (studentAssessments.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new StudentAssessmentAdapter(studentAssessments, StudentAssessmentActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setItemClickCallback(this);
            // hideProgressDialog();
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(StudentAssessmentActivity.this, StudentViewDetailsActivity.class);
        intent.putExtra(STU_ID, studentId);

        startActivity(intent);

    }

    @Override
    public void onItemClick(int p) {

    }

    @Override
    public void onEditClick(int p) {

    }

    @Override
    public void onAddClick(int p) {
        Week week = weeks.get(p);
        Intent intent = new Intent(WeeksListActivity.this, TutorialListActivity.class);
        intent.putExtra(WEEK_ID, String.valueOf(week.getId()));
        startActivity(intent);

    }
}
