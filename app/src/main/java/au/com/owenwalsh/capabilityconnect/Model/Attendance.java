package au.com.owenwalsh.capabilityconnect.Model;

/**
 * Created by Zikri on 23/10/2016.
 */

public class Attendance {
    private String studentId;
    private int weekId;
    private int attended;

    public Attendance() {
    }

    public Attendance(String studentId, int weekId, int attended) {
        this.studentId = studentId;
        this.weekId = weekId;
        this.attended = attended;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }
}
