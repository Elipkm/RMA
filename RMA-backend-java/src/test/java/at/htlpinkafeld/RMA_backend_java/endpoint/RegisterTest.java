package at.htlpinkafeld.RMA_backend_java.endpoint;

import at.htlpinkafeld.RMA_backend_java.UnitTestMockBinder;
import at.htlpinkafeld.RMA_backend_java.pojo.Credentials;
import at.htlpinkafeld.RMA_backend_java.endpoint.authentication.Register;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    private Register register;

    @BeforeEach
    public void setup(){
        register = new Register();
        ServiceLocator locator = ServiceLocatorUtilities.bind(new UnitTestMockBinder());
        locator.inject(register);
    }

    @Test
    public void successfulRegister() {
        Credentials credentials = new Credentials("joe","doe");
        Response response = register.register(credentials);

        assertEquals(200,response.getStatus());
    }
    @Test
    public void failRegister(){
        Credentials credentials = new Credentials("elias","any");
        Response response = register.register(credentials);

        assertEquals(409,response.getStatus());
    }
}