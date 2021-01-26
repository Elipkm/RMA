package at.htlpinkafeld.RMA_backend_java.service;

import at.htlpinkafeld.RMA_backend_java.mock.UserDaoMock;
import at.htlpinkafeld.RMA_backend_java.pojo.PossibleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private Login login;

    @BeforeEach
    public void setup(){
        login = new Login(new UserDaoMock());
    }

    @Test
    public void successfulLogin() {
        PossibleUser possibleUser = new PossibleUser("elias","secret");
        Response response = login.login(possibleUser);

        assertEquals(200,response.getStatus());
    }

    @Test
    public void failLogin(){
        PossibleUser possibleUser = new PossibleUser("joe","doe");
        Response response = login.login(possibleUser);

        assertEquals(403,response.getStatus());
    }

}