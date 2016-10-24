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

import au.com.owenwalsh.capabilityconnect.Database.NotesLogic;
import au.com.owenwalsh.capabilityconnect.Database.StudentAssessmentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Note;
import au.com.owenwalsh.capabilityconnect.Model.StudentAssessment;
import au.com.owenwalsh.capabilityconnect.R;

public class AddStudentAssessmentMarkActivity extends BaseActivity {
    private StudentAssessmentLogic studentAssessmentLogic;
    private StudentAssessment studentAssessment;
    private String studentID;
    private int assessmentID;
    private String assignmentName;
    private long feedback;
    // private EditText assessment_name;
    private EditText input_assessment_mark;
    private Button btn_add_assessment_mark;
    private static String STU_ID = "stuID";
    private static String ASS_ID = "assID";
    private static final String ASS_NAME = "assName";
    private SeekBar markSeekBar;
    private TextView seekbarCurrentValue;
    private TextView assessment_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_add_student_assessment_mark, null, false);
        drawerLayout.addView(contentView, 0);
        assessment_name = (TextView) findViewById(R.id.assessment_name);
        btn_add_assessment_mark = (Button) findViewById(R.id.btn_add_assessment_mark);

        Intent intent = getIntent();
        studentID = intent.getStringExtra(STU_ID);
        assessmentID = Integer.parseInt(intent.getStringExtra(ASS_ID));
        assignmentName = intent.getStringExtra(ASS_NAME);
        studentAssessment = new StudentAssessment();
        assessment_name.setText(assignmentName);
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
                Toast.makeText(AddStudentAssessmentMarkActivity.this, "Mark selection started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AddStudentAssessmentMarkActivity.this, "Mark selection finished", Toast.LENGTH_SHORT).show();
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
        btn_add_assessment_mark.setEnabled(false);
        studentAssessment = new StudentAssessment(assessmentID, studentID, mark);
        studentAssessmentLogic = new StudentAssessmentLogic(AddStudentAssessmentMarkActivity.this);
        feedback = studentAssessmentLogic.insertAssessmentMark(studentAssessment);
        if (feedback > 0) {
            addMarkSuccessful();
           // Intent intent = new Intent(AddStudentAssessmentMarkActivity.this, StudentViewDetailsActivity.class);
            //startActivity(intent);
        } else {
            addMarkFailed();
        }
    }


    private void addMarkFailed() {
        Toast.makeText(AddStudentAssessmentMarkActivity.this, "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_add_assessment_mark.setEnabled(true);
    }

    private void addMarkSuccessful() {
        Toast.makeText(AddStudentAssessmentMarkActivity.this, "Mark added successfully!", Toast.LENGTH_SHORT).show();
        btn_add_assessment_mark.setEnabled(true);
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
