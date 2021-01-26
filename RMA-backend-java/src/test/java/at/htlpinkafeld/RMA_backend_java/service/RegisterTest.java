package at.htlpinkafeld.RMA_backend_java.service;

import at.htlpinkafeld.RMA_backend_java.pojo.PossibleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    private Register register;
    @BeforeEach
    public void setup(){
        register = new Register();
    }

    @Test
    public void successfulRegister() {
        PossibleUser possibleUser = new PossibleUser("joe","doe");
        Response response = register.register(possibleUser);

        assertEquals(200,response.getStatus());
    }
    @Test
    public void failRegister(){
        PossibleUser possibleUser = new PossibleUser("elias","any");
        Response response = register.register(possibleUser);

        assertEquals(409,response.getStatus());
    }
}