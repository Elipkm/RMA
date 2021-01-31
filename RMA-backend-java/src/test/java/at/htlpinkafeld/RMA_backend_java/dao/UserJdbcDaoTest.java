package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import hthurow.tomcatjndi.TomcatJNDI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

class UserJdbcDaoTest {

    private UserJdbcDao jdbcDao;
    
    @BeforeAll
    static void init() {
        String pathContextFile = new File("src/main/webapp/META-INF/context.xml").getAbsolutePath();

        TomcatJNDI tomcatJNDI = new TomcatJNDI();
        tomcatJNDI.processContextXml(new File(pathContextFile));
        tomcatJNDI.start();
    }

    @BeforeEach
    void setUp() {
        jdbcDao = new UserJdbcDao();
    }
    
    @Test
    void delete() {
        try {
            User u = new User("__deleteTest", "myTestPassword");
            jdbcDao.create(u);
            int idElementToDel = u.getID();

            int noe = jdbcDao.list().size();

            User tmp = new User("", "");
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
        User u = new User("__deleteTestExc", "myTestPassword");
        u.setID(999999);
        assertThrows(DaoSysException.class, () -> {
            jdbcDao.delete(u);
        });
    }

    @Test
    void create() {
        try {
            int noe = jdbcDao.list().size();

            User u = new User("__createTest", "myTestPassword");
            jdbcDao.create(u);

            int id = u.getID();

            assertTrue(jdbcDao.list().size() == noe+1);
            assertTrue(jdbcDao.read(id) != null);

            jdbcDao.delete(u);
        } catch (DaoSysException e) {
            fail();
        }
    }

    @Test
    void update() {
        try {
            User u = new User("__updateTest", "myTestPassword");

            jdbcDao.create(u);
            int id = u.getID();

            u.setUsername("__new.username.fortest@user");
            u.setPassword("__newPassword", true);

            jdbcDao.update(u);
            assertTrue("__new.username.fortest@user".equals(jdbcDao.read(id).getUsername()));
            assertTrue("__newPassword".equals(jdbcDao.read(id).getEncodedPassword()));

            jdbcDao.delete(u);
        } catch (DaoSysException e) {
            fail();
        }
    }

    @Test
    void testUpdateException() {
        User u = new User("__updateTestExc", "myTestPassword");
        u.setID(9999991);
        assertThrows(DaoSysException.class, () -> {
            jdbcDao.update(u);
        });
    }
}