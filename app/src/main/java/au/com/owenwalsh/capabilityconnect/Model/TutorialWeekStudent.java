package au.com.owenwalsh.capabilityconnect.Model;

/**
 * Created by owenw on 11/10/2016.
 */
public class TutorialWeekStudent {
    private int tutorialId;
    private int weekId;
    private String studentId;
    private boolean attendance;

    public TutorialWeekStudent() {
    }

    public TutorialWeekStudent(int tutorialId, int weekId, String studentId, boolean attendance) {
        this.tutorialId = tutorialId;
        this.weekId = weekId;
        this.studentId = studentId;
        this.attendance = attendance;
    }

    public int getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(int tutorialId) {
        this.tutorialId = tutorialId;
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }
}
