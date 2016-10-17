package au.com.owenwalsh.capabilityconnect.Model;

/**
 * Created by owenw on 11/10/2016.
 */
public class Tutorial {
    private int id;
    private String day;
    private String time;
    private int numberOfStudents;

    public Tutorial() {
    }

    public Tutorial(String day, String time) {
        this.day = day;
        this.time = time;
    }


    public Tutorial(String day, String time, int numberOfStudents) {
        this.day = day;
        this.time = time;
        this.numberOfStudents  = numberOfStudents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Tutorial(int id, String day, String time, int numberOfStudents) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.numberOfStudents = numberOfStudents;
    }

    public int getNumberOfStudents() {

        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
