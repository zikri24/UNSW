package au.com.owenwalsh.capabilityconnect.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import au.com.owenwalsh.capabilityconnect.Adapters.TutorialAdapter;
import au.com.owenwalsh.capabilityconnect.Database.TutorialLogic;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.Model.Tutorial;
import au.com.owenwalsh.capabilityconnect.R;

import static au.com.owenwalsh.capabilityconnect.R.anim.actionbar_close;
import static au.com.owenwalsh.capabilityconnect.R.anim.actionbar_open;
import static au.com.owenwalsh.capabilityconnect.R.anim.rotate_backward;
import static au.com.owenwalsh.capabilityconnect.R.anim.rotate_forward;

public class TutorialListActivity extends BaseActivity implements View.OnClickListener, TutorialAdapter.ItemClickCallback {

    private static String TUT_ID = "tutorialId";
    private static String WEEK_ID = "weekId";

    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton addTutorialActionBar;
    private Animation actionbar_open, actionbar_close, rotate_forward, rotate_backward;
    private TextView emptyView;

    private TutorialLogic tutorialLogic;
    private ArrayList<Tutorial> tutorials;
    private TutorialAdapter adapter;
    private Tutorial tutorial;
    private String weekId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_tutorial_list, null, false);
        drawerLayout.addView(contentView, 0);

        initViews();


        addActionBar = (FloatingActionButton) findViewById(R.id.fab);
        addTutorialActionBar = (FloatingActionButton) findViewById(R.id.fab1);
        actionbar_open = AnimationUtils.loadAnimation(TutorialListActivity.this, R.anim.actionbar_open);
        actionbar_close = AnimationUtils.loadAnimation(TutorialListActivity.this, R.anim.actionbar_close);
        rotate_forward = AnimationUtils.loadAnimation(TutorialListActivity.this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(TutorialListActivity.this, R.anim.rotate_backward);

        addActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        addTutorialActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TutorialListActivity.this, "Add student selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Add student selected");
                //move user to AddStudentActivity
                Intent intent = new Intent(getApplicationContext(), AddTutorialActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        weekId = intent.getStringExtra(WEEK_ID);
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_tutorial_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadTutorials();
    }

    private void loadTutorials() {
        tutorialLogic = new TutorialLogic(TutorialListActivity.this);
        tutorials = tutorialLogic.findAllTutorials();
        if (tutorials.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new TutorialAdapter(tutorials, TutorialListActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.setItemClickCallback(this);
        }
    }

    public void animateFAB() {

        if (isFabOpen) {

            addActionBar.startAnimation(rotate_backward);
            addTutorialActionBar.startAnimation(actionbar_close);
            addTutorialActionBar.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            addActionBar.startAnimation(rotate_forward);
            addTutorialActionBar.startAnimation(actionbar_open);
            addTutorialActionBar.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");
        }
    }

    public void hideFloatingActionBar() {
        addTutorialActionBar.startAnimation(actionbar_close);
        addTutorialActionBar.setClickable(false);
        addTutorialActionBar.hide();
        addActionBar.hide();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(int p) {
        tutorial = tutorials.get(p);
        Intent intent = new Intent(this, TutorialStudentsListActivity.class);
        intent.putExtra(TUT_ID, String.valueOf(tutorial.getId()));
        intent.putExtra(WEEK_ID, weekId);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int p) {
        tutorial = tutorials.get(p);
        new AlertDialog.Builder(this)
                .setTitle("Deleting " + tutorial.getDay() + " " + tutorial.getTime() + "tutorial")
                .setMessage("Are you sure you want to delete " + tutorial.getDay() + " " + tutorial.getTime() + "tutorial")
                .setIcon(R.drawable.warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        tutorialLogic.deleteTutorial(tutorial.getId());
                        Toast.makeText(TutorialListActivity.this, "Tutorial has been deleted ", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttendanceClick(int p) {
        tutorial = tutorials.get(p);
        Intent intent = new Intent(this, StudentAttendanceActivity.class);
        intent.putExtra(TUT_ID, String.valueOf(tutorial.getId()));
        intent.putExtra(WEEK_ID, weekId);
        startActivity(intent);
    }
}
