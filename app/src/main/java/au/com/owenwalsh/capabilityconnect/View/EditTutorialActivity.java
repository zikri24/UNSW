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
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.Database.TutorialLogic;
import au.com.owenwalsh.capabilityconnect.Model.Tutorial;
import au.com.owenwalsh.capabilityconnect.R;

public class EditTutorialActivity extends BaseActivity {
    private Spinner spinner_days;
    private TimePicker picker_time;
    private Button btn_update_tutorial;

    private TutorialLogic tutorialLogic;
    private Tutorial tutorial;
    private long feedback;
    private static String TUT_ID = "tutorialId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_edit_tutorial, null, false);
        drawerLayout.addView(contentView, 0);

        spinner_days = (Spinner) findViewById(R.id.spinner_days);
        picker_time = (TimePicker) findViewById(R.id.picker_time);

        btn_update_tutorial = (Button) findViewById(R.id.btn_add_tutorial);

        Intent intent = getIntent();
        final String tutorialIDString = intent.getStringExtra(TUT_ID);
        int tutorialID = Integer.parseInt(tutorialIDString);
        tutorialLogic = new TutorialLogic(EditTutorialActivity.this);
        Tutorial tutorial = tutorialLogic.findTutorialById(tutorialID);

        btn_update_tutorial.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                updateTutorial();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateTutorial() {
        if(!validateTutorial()){
            updateTutorialFailed();
            return;
        }
        btn_update_tutorial.setEnabled(false);
        //add logic here for adding student
        int tutorialHour = picker_time.getHour();
        int tutorialMinute = picker_time.getMinute();
        String time = String.valueOf(tutorialHour+ ":" + tutorialMinute);
        String day = spinner_days.getSelectedItem().toString();
        tutorial = new Tutorial(day,time);
        tutorialLogic = new TutorialLogic(EditTutorialActivity.this);
        feedback = tutorialLogic.addTutorial(tutorial);
        if(feedback > 0){
            updateTutorialSuccessfull();
            Intent intent = new Intent(EditTutorialActivity.this, TutorialListActivity.class);
            startActivity(intent);
        }else{
            updateTutorialFailed();
        }
    }


    private void updateTutorialFailed() {
        Toast.makeText(EditTutorialActivity.this, "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_update_tutorial.setEnabled(true);
    }

    private void updateTutorialSuccessfull(){
        Toast.makeText(EditTutorialActivity.this, "Tutorial updated successfully!", Toast.LENGTH_SHORT).show();
        btn_update_tutorial.setEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean validateTutorial() {
        boolean validated = true;
        //validation logic here
        int hour = picker_time.getHour();
        int minute = picker_time.getMinute();
        String day = spinner_days.getSelectedItem().toString();

        if (hour <=0 && minute <=0) {
            Toast.makeText(EditTutorialActivity.this, "Tutorial time must be entered", Toast.LENGTH_SHORT).show();
            validated = false;
        } else {
            Log.d("TimePicker", "Inputted successfully");
        }
        if (day.isEmpty()){
            Toast.makeText(EditTutorialActivity.this, "Tutorial day must be entered", Toast.LENGTH_SHORT).show();
            validated = false;
        } else {
            Log.d("Day Spinner", "Inputted successfully");
        }
        return validated;
    }

    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            String selected = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {
            Toast.makeText(EditTutorialActivity.this, "No day was selected", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getSpinnerTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        //time = hour + ":" + min;
    }
}
