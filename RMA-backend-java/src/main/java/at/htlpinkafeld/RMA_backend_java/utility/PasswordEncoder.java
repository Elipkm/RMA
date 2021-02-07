package at.htlpinkafeld.RMA_backend_java.utility;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class PasswordEncoder {

    private String encodedPassword = "";
    private final org.springframework.security.crypto.password.PasswordEncoder encoderTool;

    public PasswordEncoder(String plainPassword) {
        this.encoderTool = new Pbkdf2PasswordEncoder();
        this.setEncodedPassword(plainPassword);
    }

    public PasswordEncoder setPassword(String password) {
        this.setEncodedPassword(password);
        return this;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }
    public boolean matches(String plainPassword, String encodedPassword){
        return this.encoderTool.matches(plainPassword,encodedPassword);
    }
    private void setEncodedPassword(String plainPassword) {
        this.encodedPassword = this.encoderTool.encode(plainPassword);
    }
}
