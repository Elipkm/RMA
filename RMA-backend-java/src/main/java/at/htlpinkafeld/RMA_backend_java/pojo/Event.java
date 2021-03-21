package at.htlpinkafeld.RMA_backend_java.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class Event implements Identifiable {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;

    @JsonCreator
    public Event(@JsonProperty("id") String id, @JsonProperty("name") String name,
                 @JsonProperty("startDate") Date startDate, @JsonProperty("endDate") Date endDate){
        if(id == null){
            this.init(-1,name,startDate,endDate);
        } else {
            this.init(Integer.parseInt(id),name,startDate,endDate);
        }
    }

    public Event(int id, String name,Date startDate, Date endDate) {
        this.init(id,name,startDate,endDate);
    }
    private void init(int id, String name, Date startDate, Date endDate){
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(String name, Date startDate, Date endDate) {
        this.init(-1,name,startDate,endDate);
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
