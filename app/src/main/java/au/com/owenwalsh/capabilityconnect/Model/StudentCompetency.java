package au.com.owenwalsh.capabilityconnect.Model;

/**
 * Created by owenw on 11/10/2016.
 */
public class StudentCompetency {
    private String studentId;
    private int competencyId;
    private int rating;

    public StudentCompetency() {
    }

    public StudentCompetency(String studentId, int competencyId, int rating) {
        this.studentId = studentId;
        this.competencyId = competencyId;
        this.rating = rating;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getCompetencyId() {
        return competencyId;
    }

    public void setCompetencyId(int competencyId) {
        this.competencyId = competencyId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
