package au.com.owenwalsh.capabilityconnect.Model;

/**
 * Created by Zikri on 23/10/2016.
 */

public class Note {
    private int noteId;
    private String note;
    private int studentId;

    public Note() {
    }

    public Note( String note, int studentId) {
        this.noteId = noteId;
        this.note = note;
        this.studentId = studentId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
