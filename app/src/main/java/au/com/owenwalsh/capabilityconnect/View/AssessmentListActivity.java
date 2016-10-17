package au.com.owenwalsh.capabilityconnect.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Adapters.StudentAdapter;
import au.com.owenwalsh.capabilityconnect.Database.AssessmentLogic;
import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Assessment;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;

public class AssessmentListActivity extends BaseActivity implements View.OnClickListener, AssessmentAdapter.ItemClickCallback {

    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton addAssessmentActionBar;
    private Animation actionbar_open, actionbar_close, rotate_forward, rotate_backward;

    private AssessmentLogic assessmentLogic;
    private ArrayList<Assessment> assessment;
    private AssessmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_assessment_list, null, false);
        drawerLayout.addView(contentView, 0);
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
        assessment = AssessmentLogic.findAllAssessments();
        adapter = new StudentAdapter(assessment, AssessmentListActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setItemClickCallback(this);
        // hideProgressDialog();
    }

    //passing the pokemon name to the detail activity on item clicked
    @Override
    public void onItemClick(int p) {
        Assessment assessment = assessment.get(p);
        Intent intent = new Intent(AssessmentListActivity.this, DummyActivity.class);
        intent.putExtra(ASSESSMENT_NAME, assessment.getAssessName());
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int p) {
        Assessment assessment = assessment.get(p);
        assessmentLogic.deleteAssessment(assessment.getId());
        new AlertDialog.Builder(AssessmentListActivity.this)
                .setTitle("Deleting " + assessment.getAssessmentName())
                .setMessage("Are you sure you want to delete " + assessment.getLastName())
                .setIcon(R.drawable.warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(AssessmentListActivity.this, "Assessment has been deleted ", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onUpdateClick(int p) {
        Assessment assessment = assessment.get(p);
        Intent intent = new Intent(this, DummyActivity.class);
        intent.putExtra(ASSESSMENT_NAME, assessment.getAssessName());
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
