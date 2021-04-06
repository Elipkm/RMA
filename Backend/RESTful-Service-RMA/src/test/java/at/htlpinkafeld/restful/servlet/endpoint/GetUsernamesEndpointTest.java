package at.htlpinkafeld.restful.servlet.endpoint;

import at.htlpinkafeld.restful.UnitTestMockBinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class GetUsernamesEndpointTest {

    private GetUsernamesEndpoint getUsernamesEndpoint;

    @BeforeEach
    public void setup(){
        getUsernamesEndpoint = new GetUsernamesEndpoint();
        new UnitTestMockBinder().injectHereForUnitTest(getUsernamesEndpoint);
    }

    @Test
    public void getUsernames() {
        Response response = getUsernamesEndpoint.getUsernames();
        assertArrayEquals(new String[]{"elias","daniel","markus","lukas"}, (Object[]) response.getEntity());
    }
}