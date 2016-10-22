package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.Database.TutorialLogic;
import au.com.owenwalsh.capabilityconnect.Model.Tutorial;
import au.com.owenwalsh.capabilityconnect.R;

public class TutorialViewDetailsActivity extends BaseActivity {
    private TutorialLogic tutorialLogic;
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton editTutorialActionBar;
    private FloatingActionButton removeTutorialActionBar;
    private Animation actionbar_open,actionbar_close,rotate_forward,rotate_backward;
    private TextView tutorial_day;
    private TextView tutorial_time;
    public static final String TUTORIAL_ID = "tutorialID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_tutorial_view_details, null, false);
        drawerLayout.addView(contentView, 0);


        tutorial_day = (TextView) findViewById(R.id.tutorial_day);
        tutorial_time = (TextView) findViewById(R.id.tutorial_time);
        getIntentItems();

        addActionBar = (FloatingActionButton) findViewById(R.id.fab);
        editTutorialActionBar = (FloatingActionButton) findViewById(R.id.fab1);
        removeTutorialActionBar = (FloatingActionButton) findViewById(R.id.fab2);
        actionbar_open = AnimationUtils.loadAnimation(TutorialViewDetailsActivity.this, R.anim.actionbar_open);
        actionbar_close = AnimationUtils.loadAnimation(TutorialViewDetailsActivity.this, R.anim.actionbar_close);
        rotate_forward = AnimationUtils.loadAnimation(TutorialViewDetailsActivity.this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(TutorialViewDetailsActivity.this, R.anim.rotate_backward);

        Intent intent = getIntent();
        final String tutorialID = intent.getStringExtra(TutorialStudentsListActivity.TUTORIAL_ID);


        addActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });

        editTutorialActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TutorialViewDetailsActivity.this, "Edit tutorial selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Add student selected");
                //move user to EditStudentActivity
                Intent intent = new Intent(getApplicationContext(), EditTutorialActivity.class);
                intent.putExtra(TUTORIAL_ID, tutorialID);
                startActivity(intent);
            }
        });

        removeTutorialActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO remove tutorial logic here
            }
        });

    }

    private void getIntentItems() {
        Intent intent = getIntent();
        final String tutIDString = intent.getStringExtra(TutorialStudentsListActivity.TUTORIAL_ID);
        final int tutorialID = Integer.parseInt(tutIDString);
        tutorialLogic = new TutorialLogic(TutorialViewDetailsActivity.this);
        Tutorial tutorial = tutorialLogic.findTutorialById(tutorialID);
        String tutorialDay = tutorial.getDay().toString();
        String tutorialTime = tutorial.getTime().toString();
        tutorial_day.setText(tutorialDay);
        tutorial_time.setText(tutorialTime);

    }


    public void animateFAB(){

        if(isFabOpen){

            addActionBar.startAnimation(rotate_backward);
            editTutorialActionBar.startAnimation(actionbar_close);
            editTutorialActionBar.setClickable(false);
            removeTutorialActionBar.startAnimation(actionbar_close);
            removeTutorialActionBar.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            addActionBar.startAnimation(rotate_forward);
            editTutorialActionBar.startAnimation(actionbar_open);
            editTutorialActionBar.setClickable(true);
            removeTutorialActionBar.startAnimation(actionbar_open);
            removeTutorialActionBar.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");
        }
    }


    public void hideFloatingActionBar(){
        editTutorialActionBar.startAnimation(actionbar_close);
        editTutorialActionBar.setClickable(false);
        editTutorialActionBar.hide();
        removeTutorialActionBar.startAnimation(actionbar_close);
        removeTutorialActionBar.setClickable(false);
        removeTutorialActionBar.hide();
        addActionBar.hide();
    }
}


