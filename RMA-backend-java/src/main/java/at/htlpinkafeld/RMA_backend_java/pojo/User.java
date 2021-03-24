package at.htlpinkafeld.RMA_backend_java.pojo;

import at.htlpinkafeld.RMA_backend_java.utility.PasswordEncoder;

import java.util.Locale;
import java.util.Objects;

public class User implements Identifiable {

    private int id;
    private String username;
    private String encodedPassword;
    private final PasswordEncoder passwordEncoder;

    public User(String username, String password) {
        this(-1, username,password);
    }

    public User(int id, String username, String password) {
        this.setUsernameWithRightFormat(username);
        this.passwordEncoder = new PasswordEncoder(password);
        this.encodedPassword = passwordEncoder.getEncodedPassword();
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.setUsernameWithRightFormat(username);
    }
    private void setUsernameWithRightFormat(String username){
        this.username = username.toLowerCase(Locale.GERMAN);
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setPassword(String password,boolean isHashed) {
        if(isHashed) {
            this.encodedPassword = password;
        } else{
            this.encodedPassword = passwordEncoder.setPassword(password).getEncodedPassword();
        }
    }

    public boolean authenticate(String username,String plainPassword){
        if(username.equals(this.username) && passwordEncoder.matches(plainPassword, this.encodedPassword)){
            return true;
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
    public int hashCode() {
        return Objects.hash(username, encodedPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && username.equals(user.username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", encodedPassword='" + encodedPassword + '\'' +
                ", passwordEncoder=" + passwordEncoder +
                ", id=" + id +
                '}';
    }
}
