package au.com.owenwalsh.capabilityconnect.Model;

/**
 * Created by owenw on 11/10/2016.
 */
public class Week {
    private int id;
    private String name;
    private String toDoNextWeek;
    private String comment;

    public Week() {
    }

    public Week(int id, String name, String toDoNextWeek, String comment) {
        this.id = id;
        this.name = name;
        this.toDoNextWeek = toDoNextWeek;
        this.comment = comment;
    }
    public Week(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToDoNextWeek() {
        return toDoNextWeek;
    }

    public void setToDoNextWeek(String toDoNextWeek) {
        this.toDoNextWeek = toDoNextWeek;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
