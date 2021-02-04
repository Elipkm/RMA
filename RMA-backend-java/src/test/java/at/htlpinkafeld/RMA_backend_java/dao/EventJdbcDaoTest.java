package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.mock.EventDaoMock;
import at.htlpinkafeld.RMA_backend_java.pojo.Event;
import hthurow.tomcatjndi.TomcatJNDI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class EventJdbcDaoTest {


    private EventJdbcDao jdbcDao;

    @BeforeAll
    static void init() {
        String pathContextFile = new File("src/main/webapp/META-INF/context.xml").getAbsolutePath();

        TomcatJNDI tomcatJNDI = new TomcatJNDI();
        tomcatJNDI.processContextXml(new File(pathContextFile));
        tomcatJNDI.start();
    }

    @BeforeEach
    void setUp() {
        jdbcDao = new EventJdbcDao();
    }

    @Test
    void delete() {
        try {
            Event e= new Event("__deleteTest", Date.valueOf("1990-01-01"), Date.valueOf("1990-01-01"));
            jdbcDao.create(e);
            int idElementToDel = e.getID();

            int noe = jdbcDao.list().size();

            Event tmp = new Event("", null,null);
            tmp.setID(idElementToDel);
            jdbcDao.delete(tmp);
            assertTrue(jdbcDao.list().size() == noe-1);
            assertTrue(jdbcDao.read(idElementToDel) == null);
        } catch (DaoSysException e) {
            fail();
        }
    }

    @Test
    void testDeleteException() {
        Event e= new Event("__deleteTestExc", Date.valueOf("1990-01-01"), Date.valueOf("1990-01-01"));
        e.setID(999999);
        assertThrows(DaoSysException.class, () -> {
            jdbcDao.delete(e);
        });
    }

    @Test
    void create() {
        try {
            int noe = jdbcDao.list().size();

            Event e = new Event("__createTest", Date.valueOf("1990-01-01"), Date.valueOf("1990-01-01"));
            jdbcDao.create(e);

            int id = e.getID();

            assertTrue(jdbcDao.list().size() == noe+1);
            assertTrue(jdbcDao.read(id) != null);

            jdbcDao.delete(e);
        } catch (DaoSysException e) {
            fail();
        }
    }

    @Test
    void update() {
        try {
            Event e = new Event("__updateTest", Date.valueOf("1990-01-01"), Date.valueOf("1990-01-01"));

            jdbcDao.create(e);
            int id = e.getID();

            e.setStartDate(Date.valueOf("2021-01-01"));
            e.setEndDate(Date.valueOf("2021-01-02"));

            jdbcDao.update(e);
            assertEquals(Date.valueOf("2021-01-01"), jdbcDao.read(id).getStartDate());
            assertEquals(Date.valueOf("2021-01-02"), jdbcDao.read(id).getEndDate());

            jdbcDao.delete(e);
        } catch (DaoSysException e) {
            fail();
        }
    }

    @Test
    void testUpdateException() {
        Event e = new Event("__updateTestExc", Date.valueOf("1990-01-01"), Date.valueOf("1990-01-01"));
        e.setID(9999991);
        assertThrows(DaoSysException.class, () -> {
            jdbcDao.update(e);
        });
    }

}