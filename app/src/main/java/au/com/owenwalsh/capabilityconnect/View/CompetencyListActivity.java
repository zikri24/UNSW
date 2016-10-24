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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Adapters.CompetencyAdapter;
import au.com.owenwalsh.capabilityconnect.Adapters.StudentAdapter;
import au.com.owenwalsh.capabilityconnect.Database.CompetencyLogic;
import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;

public class CompetencyListActivity extends BaseActivity implements View.OnClickListener, CompetencyAdapter.ItemClickCallback {
    public static final String COMP_ID = "competencyID";
    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton addCompetencyActionBar;
    private Animation actionbar_open, actionbar_close, rotate_forward, rotate_backward;
    private TextView emptyView;

    private CompetencyLogic competencyLogic;
    private ArrayList<Competency> competencies;
    private CompetencyAdapter adapter;
    private Competency competency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_competency_list, null, false);
        drawerLayout.addView(contentView, 0);


        initViews();

        addActionBar = (FloatingActionButton) findViewById(R.id.fab);
        addCompetencyActionBar = (FloatingActionButton) findViewById(R.id.fab1);
        actionbar_open = AnimationUtils.loadAnimation(CompetencyListActivity.this, R.anim.actionbar_open);
        actionbar_close = AnimationUtils.loadAnimation(CompetencyListActivity.this, R.anim.actionbar_close);
        rotate_forward = AnimationUtils.loadAnimation(CompetencyListActivity.this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(CompetencyListActivity.this, R.anim.rotate_backward);

        addActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        addCompetencyActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompetencyListActivity.this, "Add student selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Add student selected");
                //move user to AddStudentActivity
                Intent intent = new Intent(getApplicationContext(), AddCompetencyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_competency_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadCompetencies();
    }

    public void animateFAB() {

        if (isFabOpen) {

            addActionBar.startAnimation(rotate_backward);
            addCompetencyActionBar.startAnimation(actionbar_close);
            addCompetencyActionBar.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            addActionBar.startAnimation(rotate_forward);
            addCompetencyActionBar.startAnimation(actionbar_open);
            addCompetencyActionBar.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }

    private void loadCompetencies() {
        //showProgressDialog();
        competencyLogic = new CompetencyLogic(CompetencyListActivity.this);
        competencies = competencyLogic.findAllCompetencies();
        if (competencies.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new CompetencyAdapter(competencies, CompetencyListActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setItemClickCallback(this);
            // hideProgressDialog();
        }
    }


    @Override
    public void onItemClick(int p) {
        competency = competencies.get(p);
        Intent intent = new Intent(CompetencyListActivity.this, EditCompetencyActivity.class);
        intent.putExtra(COMP_ID, competency.getId());
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int p) {
        competency = competencies.get(p);
        new AlertDialog.Builder(CompetencyListActivity.this)
                .setTitle("Deleting " + competency.getName())
                .setMessage("Are you sure you want to delete " + competency.getName())
                .setIcon(R.drawable.warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        competencyLogic.deleteCompetency(competency.getId());
                        Toast.makeText(CompetencyListActivity.this, "Competency has been deleted ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CompetencyListActivity.this, CompetencyListActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onUpdateClick(int p) {
       /* Competency competency = competencies.get(p);
        Intent intent = new Intent(this, EditCompetencyActivity.class);
        intent.putExtra(COMP_ID, competency.getId());
        startActivity(intent);*/
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
    private void hideProgressDialog() {
        if (progress != null && progress.isShowing()) {
            progress.hide();
            progress = null;
        }
    }

    public void hideFloatingActionBar() {
        addCompetencyActionBar.startAnimation(actionbar_close);
        addCompetencyActionBar.setClickable(false);
        addCompetencyActionBar.hide();
        addActionBar.hide();
    }

    @Override
    public void onClick(View v) {

    }


}


