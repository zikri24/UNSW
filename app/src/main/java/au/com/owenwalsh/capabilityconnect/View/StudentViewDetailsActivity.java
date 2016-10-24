package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Database.TutorialWeekStudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;

public class StudentViewDetailsActivity extends BaseActivity {
    private StudentLogic studentLogic;
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton editStudentActionBar;
    private FloatingActionButton removeStudentActionBar;
    private FloatingActionButton emailStudentActionBar;
    private Animation actionbar_open, actionbar_close, rotate_forward, rotate_backward;
    private TextView student_first_name;
    private TextView student_last_name;
    private TextView student_id;
    private TextView student_email;
    private TextView student_stream;
    private TextView student_strength;
    private TextView student_weakness;
    private ImageView image_email;
    private ImageView image_stream;
    private ImageView image_strength;
    private ImageView image_weakness;
    private static final String STU_ID = "stuID";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastname";
    private Button btn_view_notes;
    private Button btn_view_competencies;
    private Button btn_view_assessments;
    private TextView attendance_score;
    private Toolbar toolbar;
    private static final String tutorialId = "tutorialId";
    private static String TUT_ID = "tutorialId";

    private String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_student_view_details, null, false);
        drawerLayout.addView(contentView, 0);


        student_first_name = (TextView) findViewById(R.id.student_first_name);
        student_last_name = (TextView) findViewById(R.id.student_last_name);
        student_id = (TextView) findViewById(R.id.student_id);
        student_email = (TextView) findViewById(R.id.student_email);
        student_stream = (TextView) findViewById(R.id.student_stream);
        student_strength = (TextView) findViewById(R.id.student_strength);
        student_weakness = (TextView) findViewById(R.id.student_weakness);
        image_email = (ImageView) findViewById(R.id.image_email);
        image_stream = (ImageView) findViewById(R.id.image_stream);
        image_strength = (ImageView) findViewById(R.id.image_strength);
        image_weakness = (ImageView) findViewById(R.id.image_weakness);
        getIntentItems();


        addActionBar = (FloatingActionButton) findViewById(R.id.fab);
        editStudentActionBar = (FloatingActionButton) findViewById(R.id.fab1);
        removeStudentActionBar = (FloatingActionButton) findViewById(R.id.fab2);
        emailStudentActionBar = (FloatingActionButton) findViewById(R.id.fab3);
        actionbar_open = AnimationUtils.loadAnimation(StudentViewDetailsActivity.this, R.anim.actionbar_open);
        actionbar_close = AnimationUtils.loadAnimation(StudentViewDetailsActivity.this, R.anim.actionbar_close);
        rotate_forward = AnimationUtils.loadAnimation(StudentViewDetailsActivity.this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(StudentViewDetailsActivity.this, R.anim.rotate_backward);

        Intent intent = getIntent();
        studentID = intent.getStringExtra(STU_ID);
        final String firstName = intent.getStringExtra(FIRST_NAME);
        final String lastName = intent.getStringExtra(LAST_NAME);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student Profile");
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NavUtils.navigateUpFromSameTask(AddAssessmentActivity.this);
                Intent intent = new Intent(StudentViewDetailsActivity.this, TutorialStudentsListActivity.class);
                intent.putExtra(TUT_ID, tutorialId);
                startActivity(intent);
            }
        });



        attendance_score = (TextView) findViewById(R.id.attendance_score);
        btn_view_notes = (Button) findViewById(R.id.btn_view_notes);
        btn_view_assessments = (Button) findViewById(R.id.btn_view_assessments);
        btn_view_competencies = (Button) findViewById(R.id.btn_view_competencies);


        btn_view_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(StudentViewDetailsActivity.this,StudentNotesActivity.class);
                intent.putExtra(STU_ID, studentID);
                intent.putExtra(FIRST_NAME, firstName);
                intent.putExtra(LAST_NAME, lastName);
                startActivity(intent);

            }
        });

        btn_view_assessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentViewDetailsActivity.this, StudentAssessmentActivity.class);
                intent.putExtra(STU_ID, studentID);
                startActivity(intent);
            }
        });

        btn_view_competencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentViewDetailsActivity.this, StudentCompetenciesActivity.class);
                intent.putExtra(STU_ID, studentID);
                startActivity(intent);
            }
        });


        addActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });

        editStudentActionBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(StudentViewDetailsActivity.this, "Edit student selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Add student selected");
                //move user to EditStudentActivity
                Intent intent = new Intent(getApplicationContext(), EditStudentActivity.class);
                intent.putExtra(STU_ID, studentID);
                intent.putExtra(FIRST_NAME, firstName);
                intent.putExtra(LAST_NAME, lastName);
                startActivity(intent);
            }
        });
        removeStudentActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO remove student logic here
            }
        });

        emailStudentActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentViewDetailsActivity.this, "Email student selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Email student selected");
                //move user to EmailStudentActivity
                Intent intent = new Intent(getApplicationContext(), SendStudentEmailActivity.class);
                intent.putExtra(STU_ID, studentID);
                startActivity(intent);
            }
        });
    }


    public void getIntentItems() {
        Intent intent = getIntent();
        final String zID = intent.getStringExtra(STU_ID);
       // final String firstName = intent.getStringExtra(FIRST_NAME);
        //final String lastName = intent.getStringExtra(LAST_NAME);
        studentLogic = new StudentLogic(StudentViewDetailsActivity.this);
        Student student = studentLogic.findStudentById(zID);
        String email = student.getEmail();
        String stream = student.getStream();
        String weakness = student.getWeakness();
        String strength = student.getStrength();
        String firstName = student.getFirsName();
        String lastName = student.getLastName();

        student_first_name.setText(firstName);
        student_last_name.setText(lastName);
        student_id.setText(zID);
        student_email.setText(email);
        student_stream.setText(stream);
        student_strength.setText(strength);
        student_weakness.setText(weakness);

    }

    public void getRecordForIntent() {
        //get the record for the first name and last name here and set the remainder of the variables
    }


    public void animateFAB() {

        if (isFabOpen) {

            addActionBar.startAnimation(rotate_backward);
            editStudentActionBar.startAnimation(actionbar_close);
            editStudentActionBar.setClickable(false);
            removeStudentActionBar.startAnimation(actionbar_close);
            removeStudentActionBar.setClickable(false);
            emailStudentActionBar.startAnimation(actionbar_close);
            emailStudentActionBar.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            addActionBar.startAnimation(rotate_forward);
            editStudentActionBar.startAnimation(actionbar_open);
            editStudentActionBar.setClickable(true);
            removeStudentActionBar.startAnimation(actionbar_open);
            removeStudentActionBar.setClickable(true);
            emailStudentActionBar.startAnimation(actionbar_open);
            emailStudentActionBar.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");
        }
    }


    public void hideFloatingActionBar() {
        editStudentActionBar.startAnimation(actionbar_close);
        editStudentActionBar.setClickable(false);
        editStudentActionBar.hide();
        removeStudentActionBar.startAnimation(actionbar_close);
        removeStudentActionBar.setClickable(false);
        removeStudentActionBar.hide();
        emailStudentActionBar.startAnimation(actionbar_close);
        emailStudentActionBar.setClickable(false);
        emailStudentActionBar.hide();
        addActionBar.hide();
    }
}
