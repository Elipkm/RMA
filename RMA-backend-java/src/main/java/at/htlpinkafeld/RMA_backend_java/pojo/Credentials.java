package at.htlpinkafeld.RMA_backend_java.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Locale;

public class Credentials implements Serializable {

    private String username,password;

    @JsonCreator
    public Credentials(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username.toLowerCase(Locale.GERMAN);
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase(Locale.GERMAN);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
