package at.htlpinkafeld.RMA_backend_java.servlet.endpoint;

import at.htlpinkafeld.RMA_backend_java.UnitTestMockBinder;
import at.htlpinkafeld.RMA_backend_java.pojo.Credentials;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class LoginEndpointTest {

    private LoginEndpoint loginEndpoint;

    @BeforeEach
    public void setup(){
        loginEndpoint = new LoginEndpoint();
        new UnitTestMockBinder().injectHereForUnitTest(loginEndpoint);
    }

    @Test
    public void testSuccessful(){
        Response response = loginEndpoint.authenticateUser(new Credentials("elias","secret"));
        assertEquals(200, response.getStatus());
    }
    @Test
    public void testUnauthorizedWrongPassword(){
        Response response = loginEndpoint.authenticateUser(new Credentials("elias", "wrong"));
        assertEquals(401, response.getStatus());
    }

    @Test
    public void testUnauthorizedUnknownUser(){
        Response response = loginEndpoint.authenticateUser(new Credentials("unknown", "pw"));
        assertEquals(401, response.getStatus());
    }

}