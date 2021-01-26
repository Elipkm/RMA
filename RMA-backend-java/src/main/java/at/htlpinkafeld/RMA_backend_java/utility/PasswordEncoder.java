package at.htlpinkafeld.RMA_backend_java.utility;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class PasswordEncoder {

    private String encodedPassword = "";

    public PasswordEncoder(String plainPassword) {
        this.setEncodedPassword(plainPassword);
    }
    public PasswordEncoder(){}

    public PasswordEncoder setPassword(String password) {
        this.setEncodedPassword(password);
        return this;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }
    public boolean matches(String plainPassword, String encodedPassword){
        return new Pbkdf2PasswordEncoder().matches(plainPassword,encodedPassword);
    }
    private void setEncodedPassword(String plainPassword) {
        this.encodedPassword = new Pbkdf2PasswordEncoder().encode(plainPassword);
    }




}
