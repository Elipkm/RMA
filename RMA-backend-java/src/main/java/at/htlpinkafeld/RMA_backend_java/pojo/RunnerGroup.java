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

    public RunnerGroup(String name, List<Runner> runnerList) {
        this.name = name;
        this.runnerList = runnerList;
        this.id = -1;
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
