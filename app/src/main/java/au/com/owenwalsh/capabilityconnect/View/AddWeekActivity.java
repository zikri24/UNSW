package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.Database.WeekLogic;
import au.com.owenwalsh.capabilityconnect.Model.Week;
import au.com.owenwalsh.capabilityconnect.R;

public class AddWeekActivity extends BaseActivity {
    private WeekLogic weekLogic;
    private Week week;

    private long feedback;
    private EditText input_week_name;
    private EditText input_week_todo;
    private EditText input_comment;
    private Button btn_add_week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_add_week, null, false);
        drawerLayout.addView(contentView, 0);

        input_week_name = (EditText) findViewById(R.id.input_week_name);
        input_week_todo = (EditText) findViewById(R.id.input_week_todo);
        input_comment = (EditText) findViewById(R.id.input_comment);
        btn_add_week = (Button) findViewById(R.id.btn_add_week);

        btn_add_week.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addWeek();
            }
        });

    }

    private void addWeek() {
        if (!validateWeek()) {
            addWeekFailed();
            return;
        }
        btn_add_week.setEnabled(false);
        //add logic here for adding student
        String weekName = input_week_name.getText().toString();
        String weekToDo = input_week_todo.getText().toString();
        String weekComment = input_comment.getText().toString();

        week = new Week(weekName);
        weekLogic = new WeekLogic(AddWeekActivity.this);
        feedback = weekLogic.insertWeek(week);
        if (feedback > 0) {
            addWeekSuccessfull();
        } else {
            addWeekFailed();
        }
    }

    private void addWeekFailed() {
        Toast.makeText(AddWeekActivity.this, "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_add_week.setEnabled(true);
    }

    private void addWeekSuccessfull() {
        Toast.makeText(AddWeekActivity.this, "Week added successfully!", Toast.LENGTH_SHORT).show();
        btn_add_week.setEnabled(true);
    }

    private boolean validateWeek() {
        boolean validated = true;
        //validation logic here
        String weekName = input_week_name.getText().toString();
        //String weekToDo = input_week_todo.getText().toString();
        // String weekComment = input_comment.getText().toString();

        if (weekName.isEmpty()) {
            input_week_name.setError("Week name cannot be empty");
            validated = false;
        } else {
            input_week_name.setError(null);
        }
        return validated;
    }

}


