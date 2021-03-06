package au.com.owenwalsh.capabilityconnect.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
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

import au.com.owenwalsh.capabilityconnect.Adapters.AssessmentAdapter;
import au.com.owenwalsh.capabilityconnect.Adapters.StudentAdapter;
import au.com.owenwalsh.capabilityconnect.Database.AssessmentLogic;
import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Assessment;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;



public class AssessmentListActivity extends BaseActivity implements View.OnClickListener, AssessmentAdapter.ItemClickCallback {



    public static final String ASSESS_ID = "assessmentId";
    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton addAssessmentActionBar;
    private Animation actionbar_open, actionbar_close, rotate_forward, rotate_backward;
    private TextView emptyView;

    private AssessmentLogic assessmentLogic;
    private ArrayList<Assessment> assessments;
    private Assessment assessment;
    private AssessmentAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_assessment_list, null, false);
        drawerLayout.addView(contentView, 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assessments");
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(AssessmentListActivity.this);
            }
        });


        initViews();

        addActionBar = (FloatingActionButton) findViewById(R.id.fab);
        addAssessmentActionBar = (FloatingActionButton) findViewById(R.id.fab1);
        actionbar_open = AnimationUtils.loadAnimation(AssessmentListActivity.this, R.anim.actionbar_open);
        actionbar_close = AnimationUtils.loadAnimation(AssessmentListActivity.this, R.anim.actionbar_close);
        rotate_forward = AnimationUtils.loadAnimation(AssessmentListActivity.this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(AssessmentListActivity.this, R.anim.rotate_backward);

        addActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        addAssessmentActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AssessmentListActivity.this, "Add student selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Add student selected");
                //move user to AddStudentActivity
                Intent intent = new Intent(getApplicationContext(), AddAssessmentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_assessment_list);
        emptyView = (TextView)  findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadAssessments();
    }

    public void animateFAB() {

        if (isFabOpen) {

            addActionBar.startAnimation(rotate_backward);
            addAssessmentActionBar.startAnimation(actionbar_close);
            addAssessmentActionBar.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            addActionBar.startAnimation(rotate_forward);
            addAssessmentActionBar.startAnimation(actionbar_open);
            addAssessmentActionBar.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }

    private void loadAssessments() {
        //showProgressDialog();
        assessmentLogic = new AssessmentLogic(AssessmentListActivity.this);
        assessments = assessmentLogic.findAllAssessments();
        if (assessments.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new AssessmentAdapter(assessments, AssessmentListActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setItemClickCallback(this);
            // hideProgressDialog();
        }
    }


    @Override
    public void onItemClick(int p) {
        Assessment assessment = assessments.get(p);
        Intent intent = new Intent(this, AssessmentViewDetailsActivity.class);
        intent.putExtra(ASSESS_ID, String.valueOf(assessment.getId()));
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int p) {
        assessment = assessments.get(p);
        new AlertDialog.Builder(AssessmentListActivity.this)
                .setTitle("Deleting " + assessment.getName())
                .setMessage("Are you sure you want to delete " + assessment.getName())
                .setIcon(R.drawable.warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        assessmentLogic.deleteAssessment(assessment.getId());
                        Toast.makeText(AssessmentListActivity.this, "Assessment has been deleted ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AssessmentListActivity.this, AssessmentListActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateClick(int p) {
        /*assessment = assessments.get(p);
        Intent intent = new Intent(this, EditAssessmentActivity.class);
        intent.putExtra(ASSESS_ID, String.valueOf(assessment.getId()));
        startActivity(intent);*/
    }

    /**
     * This method is a reusable progress dialog to alert the users that we are waitinf for data
     */
    private void showProgressDialog() {
        if (progress == null) {
            progress = ProgressDialog.show(getApplicationContext(), "loading...","wont be long!", true);
        }
    }
    /**
     * This method hides the progress dialog and resets it to null
     */
    private void hideProgressDialog() {
        if (progress != null && progress.isShowing()) {
            progress.hide();
            progress = null;
        }
    }

    public void hideFloatingActionBar(){
        addAssessmentActionBar.startAnimation(actionbar_close);
        addAssessmentActionBar.setClickable(false);
        addAssessmentActionBar.hide();
        addActionBar.hide();
    }

    @Override
    public void onClick(View v) {

    }


}
