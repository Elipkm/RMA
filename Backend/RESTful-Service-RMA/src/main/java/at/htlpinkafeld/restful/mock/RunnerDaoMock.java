package at.htlpinkafeld.restful.mock;

import at.htlpinkafeld.restful.dao.RunnerDao;
import at.htlpinkafeld.restful.pojo.Runner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class RunnerDaoMock extends GenericMock<Runner> implements RunnerDao {
    public RunnerDaoMock() {
        super(new ArrayList<>(Arrays.asList(
                new Runner(1,"Elias","Erkinger", Date.valueOf(LocalDate.of(2003,3,25))),
                new Runner(2,"Daniel", "Mader", Date.valueOf(LocalDate.of(2003,2,24)))
                )));
    }
}
