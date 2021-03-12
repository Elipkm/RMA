package at.htlpinkafeld.RMA_backend_java.servlet.endpoint;

import at.htlpinkafeld.RMA_backend_java.UnitTestMockBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class GetUsernamesTest {

    private GetUsernames getUsernames;

    @BeforeEach
    public void setup(){
        getUsernames = new GetUsernames();
        ServiceLocatorUtilities.bind(new UnitTestMockBinder()).inject(getUsernames);
    }

    @Test
    public void getUsernames() {
        Response response = getUsernames.getUsernames();
        assertArrayEquals(new String[]{"elias","daniel","markus","lukas"}, (Object[]) response.getEntity());
    }
}