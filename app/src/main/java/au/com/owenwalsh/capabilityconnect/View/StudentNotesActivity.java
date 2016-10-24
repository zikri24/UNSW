package au.com.owenwalsh.capabilityconnect.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import au.com.owenwalsh.capabilityconnect.Adapters.NotesAdapter;
import au.com.owenwalsh.capabilityconnect.Adapters.StudentAssessmentAdapter;
import au.com.owenwalsh.capabilityconnect.Database.NotesLogic;
import au.com.owenwalsh.capabilityconnect.Database.StudentAssessmentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Note;
import au.com.owenwalsh.capabilityconnect.R;

public class StudentNotesActivity extends BaseActivity implements View.OnClickListener, NotesAdapter.ItemClickCallback  {
    private static final String STU_ID = "stuID";
    private static final String NOTE_ID = "noteID";
    private RecyclerView recyclerView;
    private TextView emptyView;

    private NotesLogic notesLogic;
    private ArrayList<Note> notes;
    private Note note;
    private NotesAdapter adapter;
    private String studentId;

    private ProgressDialog progress;
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton addNoteActionBar;
    private Animation actionbar_open, actionbar_close, rotate_forward, rotate_backward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_student_notes, null, false);
        drawerLayout.addView(contentView, 0);

        Intent intent = getIntent();
        studentId = intent.getStringExtra(STU_ID);

        initViews();

        addActionBar = (FloatingActionButton) findViewById(R.id.fab);
        addNoteActionBar = (FloatingActionButton) findViewById(R.id.fab1);
        actionbar_open = AnimationUtils.loadAnimation(StudentNotesActivity.this, R.anim.actionbar_open);
        actionbar_close = AnimationUtils.loadAnimation(StudentNotesActivity.this, R.anim.actionbar_close);
        rotate_forward = AnimationUtils.loadAnimation(StudentNotesActivity.this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(StudentNotesActivity.this, R.anim.rotate_backward);

        addActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });

        addNoteActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentNotesActivity.this, "Add note selected", Toast.LENGTH_SHORT).show();
                Log.d("FAB FOCUSED:", "Add note selected");
                //move user to AddStudentActivity
                Intent intent = new Intent(getApplicationContext(), AddStudentNotesActivity.class);
                startActivity(intent);
            }
        });


    }

    private void animateFAB() {
        if (isFabOpen) {

            addActionBar.startAnimation(rotate_backward);
            addNoteActionBar.startAnimation(actionbar_close);
            addNoteActionBar.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            addActionBar.startAnimation(rotate_forward);
            addNoteActionBar.startAnimation(actionbar_open);
            addNoteActionBar.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");
        }
    }

    public void hideFloatingActionBar() {
        addNoteActionBar.startAnimation(actionbar_close);
        addNoteActionBar.setClickable(false);
        addNoteActionBar.hide();
        addActionBar.hide();
    }
    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_student_notes_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadNotes();
    }

    private void loadNotes() {
        notesLogic = new NotesLogic(StudentNotesActivity.this);
        notes = notesLogic.findNotesByStudentId(studentId);
        if (notes.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter = new NotesAdapter(notes, StudentNotesActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setItemClickCallback(this);
            // hideProgressDialog();
        }
    }

    @Override
    public void onClick(View v) {
/*
        Intent intent = new Intent(StudentAssessmentActivity.this, StudentViewDetailsActivity.class);
        intent.putExtra(STU_ID, studentId);

        startActivity(intent);
*/

    }

    @Override
    public void onDeleteClick(int p) {

    }
}

