package au.com.owenwalsh.capabilityconnect.Model;

/**
 * Created by Zikri on 23/10/2016.
 */

public class Note {
    private int noteId;
    private String content;
    private String studentId;

    public Note() {
    }

    public Note(String content, String studentId) {
        this.noteId = noteId;
        this.content = content;
        this.studentId = studentId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
