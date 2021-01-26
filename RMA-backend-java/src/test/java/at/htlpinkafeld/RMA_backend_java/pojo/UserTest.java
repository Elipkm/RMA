package at.htlpinkafeld.RMA_backend_java.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;
    @BeforeEach
    public void setup(){
        user = new User("DAVID","myPassword");
    }

    @Test
    public void getUsername(){
        assertEquals("david",user.getUsername());
    }

    @Test
    public void setUsername() {
        user.setUsername("zach");
        assertEquals("zach",user.getUsername());
    }

}