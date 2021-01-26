package at.htlpinkafeld.RMA_backend_java.pojo;

import at.htlpinkafeld.RMA_backend_java.dao.Identifiable;
import at.htlpinkafeld.RMA_backend_java.utility.PasswordEncoder;

import java.util.Objects;

public class User implements Identifiable {

    private String username;
    private String encodedPassword;

    private int id = -1;

    public User(String username, String password) {
        this.setUsername(username);
        this.encodedPassword = new PasswordEncoder(password).getEncodedPassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setPassword(String password,boolean isHashed) {
        if(isHashed) {
            this.encodedPassword = password;
        } else{
            this.encodedPassword = new PasswordEncoder(password).getEncodedPassword();
        }
    }

    public boolean authenticate(String username,String plainPassword){
        if(username.equals(this.username)){
            if(new PasswordEncoder().matches(plainPassword,this.encodedPassword)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + encodedPassword + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, encodedPassword);
    }
}
