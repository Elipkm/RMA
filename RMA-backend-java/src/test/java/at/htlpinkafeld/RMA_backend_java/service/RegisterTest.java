package at.htlpinkafeld.RMA_backend_java.service;

import at.htlpinkafeld.RMA_backend_java.pojo.Credentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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