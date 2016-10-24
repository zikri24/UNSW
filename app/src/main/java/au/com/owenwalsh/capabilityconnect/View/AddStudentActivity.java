package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;

public class AddStudentActivity extends BaseActivity {
    private StudentLogic studentLogic;
    private Student student;
    private int tutorialId;
    private long feedback;
    private EditText input_firstName;
    private EditText input_lastName;
    private EditText input_email;
    private EditText input_zID;
    private Button btn_addStudent;
    private EditText input_stream;
    private static String TUT_ID = "tutorialId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_add_student, null, false);
        drawerLayout.addView(contentView, 0);

        Intent intent = getIntent();
        tutorialId = Integer.parseInt(intent.getStringExtra(TUT_ID));


        input_firstName = (EditText) findViewById(R.id.input_first_name);
        input_lastName = (EditText) findViewById(R.id.input_last_name);
        input_email = (EditText) findViewById(R.id.input_email);
        input_zID = (EditText) findViewById(R.id.input_zid);
        btn_addStudent = (Button) findViewById(R.id.btn_add_student);
        input_stream = (EditText) findViewById(R.id.input_stream);
        btn_addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

    }

    private void addStudent() {
        if (!validateStudent()) {
            addStudentFailed();
            return;
        }
        btn_addStudent.setEnabled(false);
        //add logic here for adding student
        String zID = input_zID.getText().toString();
        String firstName = input_firstName.getText().toString();
        String lastName = input_lastName.getText().toString();
        String email = input_email.getText().toString();
        String stream = input_stream.getText().toString();
        student = new Student(zID, firstName, lastName, email, stream);
        studentLogic = new StudentLogic(AddStudentActivity.this);
        feedback = studentLogic.insertStudent(student, tutorialId);
        if (feedback > 0) {
            addStudentSuccessfull();
            Intent intent = new Intent(AddStudentActivity.this, TutorialListActivity.class);
            startActivity(intent);
        } else {
            addStudentFailed();
        }
    }

    private void addStudentFailed() {
        Toast.makeText(AddStudentActivity.this, "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_addStudent.setEnabled(true);
    }

    private void addStudentSuccessfull() {
        Toast.makeText(AddStudentActivity.this, "Student added successfully!", Toast.LENGTH_SHORT).show();
        btn_addStudent.setEnabled(true);
    }

    private boolean validateStudent() {
        boolean validated = true;
        //validation logic here
        String firstName = input_firstName.getText().toString();
        String lastName = input_lastName.getText().toString();
        String email = input_email.getText().toString();
        String stream = input_stream.getText().toString();
        String zID = input_zID.getText().toString();

        if (firstName.isEmpty()) {
            input_firstName.setError("First name cannot be empty");
            validated = false;
        } else {
            input_firstName.setError(null);
        }
        if (lastName.isEmpty()) {
            input_lastName.setError("Last name cannot be empty");
            validated = false;
        } else {
            input_lastName.setError(null);
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("Email is not valid");
            validated = false;
        } else {
            input_email.setError(null);
        }
        if (stream.isEmpty()) {
            input_stream.setError("Stream cannot be empty");
            validated = false;
        } else {
            input_stream.setError(null);
        }
        if (zID.isEmpty() && (zID.length()>8 || zID.length()<8)) {
            input_zID.setError("zID is incorrect");
            validated = false;
        } else {
            input_zID.setError(null);
        }
        return validated;
    }

}
