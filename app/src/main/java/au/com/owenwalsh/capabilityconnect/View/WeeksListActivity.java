package au.com.owenwalsh.capabilityconnect.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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

import au.com.owenwalsh.capabilityconnect.Adapters.WeeksAdapter;
import au.com.owenwalsh.capabilityconnect.Database.WeekLogic;
import au.com.owenwalsh.capabilityconnect.Model.Week;
import au.com.owenwalsh.capabilityconnect.R;

public class WeeksListActivity extends BaseActivity implements View.OnClickListener, WeeksAdapter.ItemClickCallback {
    public static String WEEK_ID = "weekId";
    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton addWeekActionBar;
    private Animation actionbar_open,actionbar_close,rotate_forward,rotate_backward;
    private TextView emptyView;

    private WeekLogic weekLogic;
    private ArrayList<Week> weeks;
    private WeeksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_weeks_list, null, false);
        drawerLayout.addView(contentView, 0);

        initViews();

        addActionBar = (FloatingActionButton) findViewById(R.id.fab);
        addWeekActionBar = (FloatingActionButton) findViewById(R.id.fab1);
        actionbar_open = AnimationUtils.loadAnimation(WeeksListActivity.this, R.anim.actionbar_open);
        actionbar_close = AnimationUtils.loadAnimation(WeeksListActivity.this,R.anim.actionbar_close);
        rotate_forward = AnimationUtils.loadAnimation(WeeksListActivity.this,R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(WeeksListActivity.this,R.anim.rotate_backward);

        addActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        addWeekActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WeeksListActivity.this, "Add week selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Add week selected");
                //move user to AddWeekActivity
                Intent intent = new Intent(getApplicationContext(), AddWeekActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_weeks_list);
        emptyView = (TextView)  findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadWeeks();
    }

    public void animateFAB(){

        if(isFabOpen){

            addActionBar.startAnimation(rotate_backward);
            addWeekActionBar.startAnimation(actionbar_close);
            addWeekActionBar.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            addActionBar.startAnimation(rotate_forward);
            addWeekActionBar.startAnimation(actionbar_open);
            addWeekActionBar.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    private void loadWeeks() {
        //showProgressDialog();
        weekLogic = new WeekLogic(WeeksListActivity.this);
        weeks = weekLogic.findAllWeeks();
        if (weeks.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new WeeksAdapter(weeks, WeeksListActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setItemClickCallback(this);
            // hideProgressDialog();
        }
    }

    @Override
    public void onItemClick(int p) {
        Week week = weeks.get(p);
        Intent intent = new Intent(WeeksListActivity.this, TutorialListActivity.class);
        intent.putExtra(WEEK_ID, String.valueOf(week.getId()));
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int p) {
        Week week = weeks.get(p);
        weekLogic.deleteWeek(week.getId());
        new AlertDialog.Builder(WeeksListActivity.this)
                .setTitle("Deleting " + week.getName())
                .setMessage("Are you sure you want to delete " +week.getName())
                .setIcon(R.drawable.warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(WeeksListActivity.this, "Week has been deleted ", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onUpdateClick(int p) {
        Week week = weeks.get(p);
        Intent intent = new Intent(this, EditWeekActivity.class);
        intent.putExtra(WEEK_ID, String.valueOf(week.getId()));
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
        addWeekActionBar.startAnimation(actionbar_close);
        addWeekActionBar.setClickable(false);
        addWeekActionBar.hide();
        addActionBar.hide();
    }

    @Override
    public void onClick(View v) {

    }


}
