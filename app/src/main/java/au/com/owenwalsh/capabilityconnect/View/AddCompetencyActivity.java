package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.Database.CompetencyLogic;
import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.R;

public class AddCompetencyActivity extends BaseActivity {
    private long feedback;
    private CompetencyLogic competencyLogic;
    private Competency competency;

    private EditText input_competency_name;
    private EditText input_competency_desc;
    private Button btn_add_competency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_add_competency, null, false);
        drawerLayout.addView(contentView, 0);

        input_competency_name = (EditText) findViewById(R.id.input_competency_name);
        input_competency_desc = (EditText) findViewById(R.id.input_competency_desc);
        btn_add_competency = (Button) findViewById(R.id.btn_add_competency);
        btn_add_competency.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                addCompetency();
            }
        });
    }
    public void addCompetency() {
        if (!validateCompetency()) {
            addCompetencyFailed();
            return;
        }
        btn_add_competency.setEnabled(false);
        String competencyName = input_competency_name.getText().toString();
        String competencyDesc = input_competency_desc.getText().toString();

        competency = new Competency(competencyName,competencyDesc);
        competencyLogic = new CompetencyLogic(this);
        feedback = competencyLogic.insertCompetency(competency);
        if (feedback > 0) {
            addCompetencySuccessfull();
        } else {
            addCompetencyFailed();
        }
    }

    private void addCompetencyFailed() {
        Toast.makeText(AddCompetencyActivity.this, "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_add_competency.setEnabled(true);
    }

    private void addCompetencySuccessfull() {
        Toast.makeText(AddCompetencyActivity.this, "Assessment added successfully!", Toast.LENGTH_SHORT).show();
        btn_add_competency.setEnabled(true);
    }
    private boolean validateCompetency() {
        boolean validated = true;
        String competencyName = input_competency_name.getText().toString();
        String competencyDesc = input_competency_desc.getText().toString();

        if (competencyName.isEmpty()) {
            input_competency_name.setError("Name cannot be empty");
            validated = false;
        } else {
            input_competency_name.setError(null);
        } if (competencyDesc.isEmpty()) {
            input_competency_desc.setError("Description cannot be emp");
            validated = false;
        } else {
            input_competency_desc.setError(null);
        }
        return validated;
    }
}
