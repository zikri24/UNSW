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
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.R;

public class SendStudentEmailActivity extends BaseActivity {
    private EditText input_email_address;
    private EditText input_subject;
    private EditText input_email_body;
    private Button btn_send_email;
    private static final String STU_ID = "stuID";
    private static final String studentId = "studentId";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_send_student_email, null, false);
        drawerLayout.addView(contentView, 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Send Email");
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NavUtils.navigateUpFromSameTask(TutorialStudentsListActivity.this);
                Intent intent = new Intent(SendStudentEmailActivity.this, StudentViewDetailsActivity.class);
                intent.putExtra(STU_ID, studentId);
                startActivity(intent);
            }
        });

        input_email_address = (EditText) findViewById(R.id.input_email_address);
        input_subject = (EditText) findViewById(R.id.input_subject);
        input_email_body = (EditText) findViewById(R.id.input_email_body);
        btn_send_email = (Button) findViewById(R.id.btn_send_email);

        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }
    private void sendEmail() {
        if (!validateEmail()) {
            sendEmailFailed();
            return;
        }
        btn_send_email.setEnabled(false);
        //add logic here for sending email
        String emailAddress = input_email_address.getText().toString();
        String subject = input_subject.getText().toString();
        String body = input_email_body.getText().toString();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL  , emailAddress);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT   , body);
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SendStudentEmailActivity.this, "You must have an email client installed.", Toast.LENGTH_SHORT).show();
        }
        sendEmaiSuccessfull();
    }

    private void sendEmailFailed() {
        Toast.makeText(SendStudentEmailActivity.this, "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_send_email.setEnabled(true);
    }

    private void sendEmaiSuccessfull(){
        Toast.makeText(SendStudentEmailActivity.this, "Email sent successfully!", Toast.LENGTH_SHORT).show();
        btn_send_email.setEnabled(true);
    }

    private boolean validateEmail() {
        boolean validated = true;
        //validation logic here
        String emailAddress = input_email_address.getText().toString();
        String subject = input_subject.getText().toString();
        String body = input_email_body.getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            input_email_address.setError("Email is not valid");
            validated = false;
        } else {
            input_email_address.setError(null);
        } if (subject.isEmpty()){
            input_subject.setError("Subject cannot be empty");
            validated = false;
        } else {
            input_subject.setError(null);
        } if (body.isEmpty()){
            input_email_body.setError("Body cannot be empty");
            validated = false;
        } else {
            input_email_body.setError(null);
        } return validated;
    }

}
