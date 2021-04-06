package at.htlpinkafeld.restful.servlet.endpoint;

import at.htlpinkafeld.restful.UnitTestMockBinder;
import at.htlpinkafeld.restful.pojo.Credentials;
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