package at.htlpinkafeld.restful.servlet.endpoint;

import at.htlpinkafeld.restful.UnitTestMockBinder;
import at.htlpinkafeld.restful.pojo.Credentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterEndpointTest {

    private RegisterEndpoint registerEndpoint;

    @BeforeEach
    public void setup(){
        registerEndpoint = new RegisterEndpoint();
        new UnitTestMockBinder().injectHereForUnitTest(registerEndpoint);
    }

    @Test
    public void successfulRegister() {
        Credentials credentials = new Credentials("joe","doe");
        Response response = registerEndpoint.register(credentials);

        assertEquals(200,response.getStatus());
    }
    @Test
    public void failRegister(){
        Credentials credentials = new Credentials("elias","any");
        Response response = registerEndpoint.register(credentials);

        assertEquals(409,response.getStatus());
    }
}