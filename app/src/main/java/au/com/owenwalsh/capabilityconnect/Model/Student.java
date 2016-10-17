package au.com.owenwalsh.capabilityconnect.Model;

/**
 * Created by owenw on 11/10/2016.
 */
public class Student {
    private String id;
    private String firsName;
    private String lastName;
    private String email;
    private String weakness;
    private String strength;
    private String stream;

    public Student() {
    }

    public Student(String id, String firsName, String lastName, String email, String stream) {
        this.id = id;
        this.firsName = firsName;
        this.lastName = lastName;
        this.email = email;
        this.stream = stream;
    }

    public Student(String id, String firsName, String lastName, String email, String weakness, String strength, String stream) {
        this.id = id;
        this.firsName = firsName;
        this.lastName = lastName;
        this.email = email;
        this.weakness = weakness;
        this.strength = strength;
        this.stream = stream;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }
}
