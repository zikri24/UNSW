package au.com.owenwalsh.capabilityconnect.Model;

/**
 * Created by owenw on 11/10/2016.
 */
public class StudentAssessment {
    private int assessmentId;
    private String studentId;
    private double studentMark;
    private String comment;

    public StudentAssessment() {
    }

    public StudentAssessment(int assessmentId, String studentId, double studentMark, String comment) {
        this.assessmentId = assessmentId;
        this.studentId = studentId;
        this.studentMark = studentMark;
        this.comment = comment;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(double studentMark) {
        this.studentMark = studentMark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
