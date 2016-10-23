package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.Database.StudentAssessmentLogic;
import au.com.owenwalsh.capabilityconnect.Model.StudentAssessment;
import au.com.owenwalsh.capabilityconnect.Model.StudentCompetency;
import au.com.owenwalsh.capabilityconnect.R;

public class AddStudentCompetencyRatingActivity extends BaseActivity {
    private StudentCompetencyLogic studentCompetencyLogic;
    private StudentCompetency studentCompetency;
    private String studentID;
    private String competencyID;
    private long feedback;
    private EditText competency_name;
    private Button btn_add_competency_mark;
    private static String STU_ID = "studentID";
    private static String COMP_ID = "competencyID";
    private SeekBar markSeekBar;
    private TextView seekbarCurrentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_add_student_competency_rating, null, false);
        drawerLayout.addView(contentView, 0);


        Intent intent = getIntent();
        studentID = intent.getStringExtra(STU_ID);
        competencyID = intent.getStringExtra(COMP_ID);
        studentCompetency = new StudentCompetency();
        competency_name.setText(studentCompetency.getCompetencyId());
        markSeekBar = (SeekBar) findViewById(R.id.mark_seek_bar);
        markSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbarCurrentValue = (TextView) findViewById(R.id.seekbar_current);
                seekbarCurrentValue.setText("Mark: " + progress);
                int mark = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AddStudentCompetencyRatingActivity.this, "Mark selection started", Toast.LENGTH_SHORT).show()
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AddStudentCompetencyRatingActivity.this, "Mark selection finished", Toast.LENGTH_SHORT).show()
            }
        });
        addMark();
    }

    private void addMark() {
        if (!validateMark()) {
            addMarkFailed();
            return;
        }
        int mark = markSeekBar.getProgress();
        btn_add_competency_mark.setEnabled(false);
        StudentCompetency = new StudentAssessment(studentID, competencyID, mark);
        StudentCompetencyLogic = new StudentCompetencyLogic(AddStudentCompetencyRatingActivity.this);
        feedback = StudentCompetencyLogic.insertMark(StudentCompetency);
        if (feedback > 0) {
            addMarkSuccessful();
            Intent intent = new Intent(AddStudentCompetencyRatingActivity.this, StudentViewDetailsActivity.class);
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
        if (markString.isEmpty()){
            Toast.makeText(this, "Please select a mark", Toast.LENGTH_SHORT).show();
            validated = false;
        } else {
            Toast.makeText(this, "Mark entered is correct", Toast.LENGTH_SHORT).show();
        }
        return validated;
    }