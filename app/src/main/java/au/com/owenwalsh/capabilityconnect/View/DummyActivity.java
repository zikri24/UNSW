package au.com.owenwalsh.capabilityconnect.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import au.com.owenwalsh.capabilityconnect.R;

public class DummyActivity extends AppCompatActivity {
    private TextView firstName;
    private TextView tutorialId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstName = (TextView) findViewById(R.id.student_first_name);
        tutorialId = (TextView) findViewById(R.id.tut_id);
        Intent intent = getIntent();
        final String name = intent.getStringExtra(StudentListActivity.FIRST_NAME);
        firstName.setText(name);

        final String tutId = intent.getStringExtra(TutorialListActivity.TUT_ID);
        tutorialId.setText(tutId);
    }




}
