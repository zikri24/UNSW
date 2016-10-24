package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.Database.StudentAssessmentLogic;
import au.com.owenwalsh.capabilityconnect.Database.StudentCompetencyLogic;
import au.com.owenwalsh.capabilityconnect.Model.StudentAssessment;
import au.com.owenwalsh.capabilityconnect.Model.StudentCompetency;
import au.com.owenwalsh.capabilityconnect.R;

public class AddStudentCompetencyRatingActivity extends BaseActivity {
    private StudentCompetencyLogic studentCompetencyLogic;
    private StudentCompetency studentCompetency;
    private String studentID;
    private int competencyID;
    private long feedback;
    private TextView competency_name;
    private Button btn_add_competency_mark;
    private static final String STU_ID = "stuID";
    private static final String COMP_ID = "competencyID";
    private SeekBar markSeekBar;
    private TextView seekbarCurrentValue;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_add_student_competency_rating, null, false);
        drawerLayout.addView(contentView, 0);
        competency_name = (TextView) findViewById(R.id.student_competency_name);
        btn_add_competency_mark = (Button) findViewById(R.id.btn_add_competency_mark);


        Intent intent = getIntent();
        studentID = intent.getStringExtra(STU_ID);
        competencyID = Integer.valueOf(intent.getStringExtra(COMP_ID));
        studentCompetency = new StudentCompetency();
//        competency_name.setText(studentCompetency.getCompetencyId());
        markSeekBar = (SeekBar) findViewById(R.id.mark_seek_bar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Competency Rating");
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NavUtils.navigateUpFromSameTask(TutorialStudentsListActivity.this);
                Intent intent = new Intent(AddStudentCompetencyRatingActivity.this, StudentCompetenciesActivity.class);
                intent.putExtra(STU_ID, studentID);
                intent.putExtra(COMP_ID, competencyID);
                startActivity(intent);
            }
        });
        markSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbarCurrentValue = (TextView) findViewById(R.id.seekbar_current);
                seekbarCurrentValue.setText("Mark: " + progress);
                int mark = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AddStudentCompetencyRatingActivity.this, "Mark selection started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AddStudentCompetencyRatingActivity.this, "Mark selection finished", Toast.LENGTH_SHORT).show();
            }
        });
        btn_add_competency_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMark();
            }
        });
    }

    private void addMark() {
        if (!validateMark()) {
            addMarkFailed();
            return;
        }
        int mark = markSeekBar.getProgress();
        btn_add_competency_mark.setEnabled(false);
        studentCompetency = new StudentCompetency(studentID, competencyID, mark);
        studentCompetencyLogic = new StudentCompetencyLogic(AddStudentCompetencyRatingActivity.this);
        feedback = studentCompetencyLogic.insertRating(studentCompetency);
        if (feedback > 0) {
            addMarkSuccessful();
            Intent intent = new Intent(AddStudentCompetencyRatingActivity.this, StudentCompetenciesActivity.class);
            intent.putExtra(STU_ID, studentID);
            startActivity(intent);
        } else {
            addMarkFailed();
        }
    }


    private void addMarkFailed() {
        Toast.makeText(AddStudentCompetencyRatingActivity.this, "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_add_competency_mark.setEnabled(true);
    }

    private void addMarkSuccessful() {
        Toast.makeText(AddStudentCompetencyRatingActivity.this, "Mark added successfully!", Toast.LENGTH_SHORT).show();
        btn_add_competency_mark.setEnabled(true);
    }

    private boolean validateMark() {
        boolean validated = true;
        int mark = markSeekBar.getProgress();
        String markString = Integer.toString(mark);
        if (markString.isEmpty()) {
            Toast.makeText(this, "Please select a mark", Toast.LENGTH_SHORT).show();
            validated = false;
        } else {
            Toast.makeText(this, "Mark entered is correct", Toast.LENGTH_SHORT).show();
        }
        return validated;
    }
}