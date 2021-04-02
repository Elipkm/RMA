package at.htlpinkafeld.restful.pojo;

import java.sql.Date;
import java.util.Map;
import java.util.Objects;

public class Run implements Identifiable{
    private int id = -1;
    private String name;
    private Date startTime;
    private Map<Integer,Runner> runnerOnTrack;

    public Run(String name, Date startTime) {
        this.name = name;
        this.startTime = startTime;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Run run = (Run) o;
        return id == run.id && Objects.equals(name, run.name) && Objects.equals(startTime, run.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startTime);
    }

    @Override
    public String toString() {
        return "Run{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
