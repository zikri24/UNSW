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

import au.com.owenwalsh.capabilityconnect.Database.NotesLogic;
import au.com.owenwalsh.capabilityconnect.Model.Note;
import au.com.owenwalsh.capabilityconnect.R;

public class AddStudentNotesActivity extends BaseActivity {
    private NotesLogic notesLogic;
    private Note note;
    private String studentID;
    private long feedback;
    //private EditText note_name;
    private EditText input_notes;
    private Button btn_add_note;
    private static String STU_ID = "studentID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_add_student_notes, null, false);
        drawerLayout.addView(contentView, 0);

        Intent intent = getIntent();
        studentID = intent.getStringExtra(STU_ID);
       // note_name = (EditText) findViewById(R.id.note_name);
        input_notes = (EditText) findViewById(R.id.input_notes);
        btn_add_note = (Button) findViewById(R.id.btn_add_note);
        addNote();

    }
    private void addNote() {
        if (!validateNote()) {
            addNoteFailed();
            return;
        }
        btn_add_note.setEnabled(false);
        //add logic here for adding note
        //String noteName = note_name.getText().toString();
        String noteContents = input_notes.getText().toString();
        note = new Note(noteContents, studentID);
        notesLogic = new NotesLogic(AddStudentNotesActivity.this);
        feedback = notesLogic.insertNote(note);
        if (feedback > 0) {
            addNoteSuccessful();
            Intent intent = new Intent(AddStudentNotesActivity.this, StudentViewDetailsActivity.class);
            startActivity(intent);
        } else {
            addNoteFailed();
        }
    }


    private void addNoteFailed() {
        Toast.makeText(AddStudentNotesActivity.this, "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_add_note.setEnabled(true);
    }

    private void addNoteSuccessful() {
        Toast.makeText(AddStudentNotesActivity.this, "Note added successfully!", Toast.LENGTH_SHORT).show();
        btn_add_note.setEnabled(true);
    }

    private boolean validateNote() {
        boolean validated = true;
        //validation logic here
        //String noteName = note_name.getText().toString();
        String noteContents = input_notes.getText().toString();

        /*if (noteName.isEmpty()) {
            note_name.setError("Note name cannot be empty");
            validated = false;
        } else {
            note_name.setError(null);
        }*/
        if (noteContents.isEmpty()){
            input_notes.setError("Lets try adding some notes");
            validated = false;
        } else {
            input_notes.setError(null);
        }
        return validated;
    }
}
