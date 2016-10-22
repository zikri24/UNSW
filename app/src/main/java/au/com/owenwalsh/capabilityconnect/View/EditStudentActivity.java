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

public class EditStudentActivity extends BaseActivity {
    private StudentLogic studentLogic;
    private Student student;
    private int tutorialId;
    private long feedback;
    private EditText input_firstName;
    private EditText input_lastName;
    private EditText input_email;
    private EditText input_zID;
    private Button btn_updateStudent;
    private EditText input_stream;
    private static final String FIRST_NAME = "firstName";
    private static final String STU_ID = "stuID";
    private static final String LAST_NAME = "lastname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_edit_student, null, false);
        drawerLayout.addView(contentView, 0);

        input_firstName = (EditText) findViewById(R.id.input_first_name);
        input_lastName = (EditText) findViewById(R.id.input_last_name);
        input_email = (EditText) findViewById(R.id.input_email);
        input_zID = (EditText) findViewById(R.id.input_zid);
        btn_updateStudent = (Button) findViewById(R.id.btn_add_student);
        input_stream = (EditText) findViewById(R.id.input_stream);

        Intent intent = getIntent();
        final String zID = intent.getStringExtra(LAST_NAME);
        final String firstName = intent.getStringExtra(FIRST_NAME);
        final String lastName = intent.getStringExtra(LAST_NAME);


        studentLogic = new StudentLogic(EditStudentActivity.this);
        Student student = studentLogic.findStudentById(zID);
//        String email = student.getEmail().toString();
//        String stream = student.getStream().toString();
        //input_email.setText(email);
      //  input_stream.setText(stream);



        input_zID.setText(zID);
        input_firstName.setText(firstName);
        input_lastName.setText(lastName);

        btn_updateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });

    }

    private void updateStudent() {
        if (!validateStudent()) {
            updateStudentFailed();
            return;
        }
        btn_updateStudent.setEnabled(false);
        //some student logic here
       //student = new Student(zID, firstName, lastName, email, stream);
        studentLogic = new StudentLogic(EditStudentActivity.this);
        feedback = studentLogic.insertStudent(student, tutorialId);
        if (feedback > 0) {
            updateStudentSuccessfull();
            Intent intent = new Intent(EditStudentActivity.this, TutorialListActivity.class);
            startActivity(intent);
        } else {
            updateStudentFailed();
        }
    }

    private void updateStudentSuccessfull() {
        Toast.makeText(EditStudentActivity.this, "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_updateStudent.setEnabled(true);
    }

    private void updateStudentFailed() {
        Toast.makeText(EditStudentActivity.this, "Student updated successfully!", Toast.LENGTH_SHORT).show();
        btn_updateStudent.setEnabled(true);
    }

    private boolean validateStudent() {
        boolean validated = true;
        //validation logic here
        String firstName = input_firstName.getText().toString();
        String lastName = input_lastName.getText().toString();
        String email = input_email.getText().toString();
        String stream = input_stream.getText().toString();

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
        return validated;
    }
}
