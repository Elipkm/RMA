package at.htlpinkafeld.RMA_backend_java.pojo;

import java.util.List;

public class RunnerGroup implements Identifiable {
    private int id;
    private String name;
    private List<Runner> runnerList;

    public RunnerGroup(int id, String name, List<Runner> runnerList) {
        this.id = id;
        this.name = name;
        this.runnerList = runnerList;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }
}
