package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.exception.DaoNotFoundException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Event;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import hthurow.tomcatjndi.TomcatJNDI;
import org.junit.jupiter.api.*;

import java.io.File;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EventJdbcDaoTest {


    private EventJdbcDao jdbcDao;

    private User user;
    private UserJdbcDao userJdbcDao;

    @BeforeAll
    private static void init() {
        String pathContextFile = new File("src/main/webapp/META-INF/context.xml").getAbsolutePath();

        TomcatJNDI tomcatJNDI = new TomcatJNDI();
        tomcatJNDI.processContextXml(new File(pathContextFile));
        tomcatJNDI.start();
    }

    @BeforeEach
    private void setUp() {
        this.user = new User("joe_doe","123");
        this.userJdbcDao = new UserJdbcDao();
        try {
            this.userJdbcDao.create(user);
        } catch (DaoSysException daoSysException) {
            daoSysException.printStackTrace();
        }
        this.jdbcDao = new EventJdbcDao();
    }

    @AfterEach
    private void afterEach() {
        try {
            this.userJdbcDao.delete(user);
        } catch (DaoSysException daoSysException) {
            daoSysException.printStackTrace();
        }
    }

    @Test
    public void delete() {
        try {
            Event event = new Event("__deleteTest", Date.valueOf("1990-01-01"), Date.valueOf("1990-01-01"));
            jdbcDao.create(user, event);
            int idElementToDel = event.getID();

            Event temp = new Event("", null, null);
            temp.setID(idElementToDel);
            try {
                jdbcDao.delete(user, temp);
            } catch (DaoNotFoundException daoNotFoundException) {
                daoNotFoundException.printStackTrace();
                fail();
            }

            assertEquals(jdbcDao.list(user).size(), 0);
            assertThrows(DaoNotFoundException.class, () -> jdbcDao.read(user, idElementToDel));
        }catch (DaoSysException daoSysException){
            daoSysException.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDeleteException() {
        Event event = new Event("__deleteTestExc", Date.valueOf("1990-01-01"), Date.valueOf("1990-01-01"));
        event.setID(999999);
        assertThrows(DaoNotFoundException.class, () -> jdbcDao.delete(user,event));
    }

    @Test
    public void create() {
        try {
            Event e = new Event("__createTest", Date.valueOf("1990-01-01"), Date.valueOf("1990-01-01"));
            jdbcDao.create(user,e);

            int id = e.getID();

            assertEquals(jdbcDao.list(user).size(), 1);
            assertNotNull(jdbcDao.read(user, id));

            jdbcDao.delete(user,e);
        } catch (DaoSysException | DaoNotFoundException e) {
            fail();
        }
    }

    @Test
    public void update() {
        try {
            Event event = new Event("__updateTest", Date.valueOf("1990-01-01"), Date.valueOf("1990-01-01"));

            jdbcDao.create(user, event);
            int id = event.getID();

            event.setStartDate(Date.valueOf("2021-01-01"));
            event.setEndDate(Date.valueOf("2021-01-02"));

            jdbcDao.update(user, event);
            assertEquals(Date.valueOf("2021-01-01"), jdbcDao.read(user, id).getStartDate());
            assertEquals(Date.valueOf("2021-01-02"), jdbcDao.read(user, id).getEndDate());

            jdbcDao.delete(user, event);
        } catch (DaoSysException | DaoNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testUpdateException() {
        Event event = new Event("__updateTestExc", Date.valueOf("1990-01-01"), Date.valueOf("1990-01-01"));
        event.setID(9999991);
        assertThrows(DaoNotFoundException.class, () -> jdbcDao.update(user,event));
    }

}