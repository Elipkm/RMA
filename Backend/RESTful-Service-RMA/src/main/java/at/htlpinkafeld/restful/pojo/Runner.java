package at.htlpinkafeld.restful.pojo;

import java.sql.Date;
import java.util.Objects;
import java.util.List;

public class Runner implements Identifiable {
    private int id = -1;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private List<Round> roundList = null;

    public Runner(String firstname, String lastname, Date birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }

    public Runner(int id, String firstname, String lastname, Date birthdate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<Round> getRoundList() {
        return roundList;
    }

    public void setRoundList(List<Round> roundList) {
        this.roundList = roundList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Runner runner = (Runner) o;
        return id == runner.id && Objects.equals(firstname, runner.firstname) && Objects.equals(lastname, runner.lastname) && Objects.equals(birthdate, runner.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, birthdate);
    }

    @Override
    public String toString() {
        return "Runner{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
