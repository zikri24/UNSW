package au.com.owenwalsh.capabilityconnect.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.Database.AssessmentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Assessment;
import au.com.owenwalsh.capabilityconnect.R;

public class EditAssessmentActivity extends BaseActivity {

    private long feedback;
    private AssessmentLogic assessmentLogic;
    private Assessment assessment;

    private EditText input_assessment_name;
    // private EditText input_assessment_desc;
    private EditText input_weighting;
    //private TimePicker picker_time;
    private DatePicker picker_date;
    private Button btn_updateAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_edit_assessment, null, false);
        drawerLayout.addView(contentView, 0);

        input_assessment_name = (EditText) findViewById(R.id.input_assessment_name);
        // input_assessment_desc = (EditText) findViewById(R.id.input_assessment_desc);
        input_weighting = (EditText) findViewById(R.id.input_weighting);
        //picker_time = (TimePicker) findViewById(R.id.picker_time);
        picker_date = (DatePicker) findViewById(R.id.picker_date);
        btn_updateAssessment = (Button) findViewById(R.id.btn_add_Assessment);

        Intent intent = getIntent();
        final String assessID = intent.getStringExtra(AssessmentListActivity.ASSESS_ID);


        assessmentLogic = new AssessmentLogic(EditAssessmentActivity.this);
       // Assessment assessment = assessmentLogic.findAssessmentByID(assessID);
//        String assessmentName = assessment.getAssessmentName().toString();
//        String weighting = assessment.getWeighting().toString();


      //  input_assessment_name.setText(assessmentName);




        btn_updateAssessment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                updateAssessment();
            }
        });
    }


    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            String selected = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {
            Toast.makeText(EditAssessmentActivity.this, "No day was selected", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getSpinnerTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        String time = String.valueOf(hour + " " + min);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getDueDate() {
        Calendar calender = Calendar.getInstance();
        String date = String.valueOf(calender.get(Calendar.DATE));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void updateAssessment() {
        if (!validateAssessment()) {
            addAssessmentFailed();
            return;
        }
        btn_updateAssessment.setEnabled(false);
        //add logic here for adding assessment
        String assessmentName = input_assessment_name.getText().toString();
        //String assessmentDesc = input_assessment_desc.getText().toString();
        int mark = Integer.parseInt(input_weighting.getText().toString());
        //String dueTime = time;
        int dueDay = picker_date.getDayOfMonth();
        int dueMonth = picker_date.getMonth();
        int dueYear = picker_date.getYear();
        String dueDate = dueDay + "/" + dueMonth + "/" + dueYear;
        assessment = new Assessment(assessmentName, dueDate, mark);
        assessmentLogic = new AssessmentLogic(EditAssessmentActivity.this);
        feedback = assessmentLogic.insertAssessment(assessment);
        if (feedback > 0) {
            addAssessmentSuccessfull();
            Intent intent = new Intent(EditAssessmentActivity.this, AssessmentListActivity.class);
            startActivity(intent);
        } else {
            addAssessmentFailed();
        }
    }

    private void addAssessmentFailed() {
        Toast.makeText(EditAssessmentActivity.this, "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_updateAssessment.setEnabled(true);
    }

    private void addAssessmentSuccessfull() {
        Toast.makeText(EditAssessmentActivity.this, "Assessment added successfully!", Toast.LENGTH_SHORT).show();
        btn_updateAssessment.setEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean validateAssessment() {
        boolean validated = true;
        //validation logic here
        String assessmentName = input_assessment_name.getText().toString();
        //String assessmentDesc = input_assessment_desc.getText().toString();
        String weighting = input_weighting.getText().toString();
        int day = picker_date.getDayOfMonth();
        int month = picker_date.getMonth();
        int year = picker_date.getYear();
        //int hour = picker_time.getHour();
        //int minute = picker_time.getMinute();

        if (assessmentName.isEmpty()) {
            input_assessment_name.setError("First name cannot be empty");
            validated = false;
        } else {
            input_assessment_name.setError(null);
        }
       /* if (assessmentDesc.isEmpty()){
            input_assessment_desc.setError("Last name cannot be empty");
            validated = false;
        } else {
            input_assessment_desc.setError(null);
        }*/
        if (weighting.isEmpty()) {
            input_weighting.setError("Stream cannot be emp");
            validated = false;
        } else {
            input_weighting.setError(null);
        }
        if (day <= 0 && month <= 0 && year <= 0) {
            Toast.makeText(EditAssessmentActivity.this, "The assessment date must not be empty", Toast.LENGTH_SHORT).show();
            validated = false;
        } else {
            Log.d("Date", "input is valid");
        }
        /*if (hour <=0 && minute <=0) {
            Toast.makeText(AddAssessmentActivity.this, "A time must be selected", Toast.LENGTH_SHORT).show();
        } else{
            Log.d("Time","input is valid");
        }*/
        return validated;
    }

}

