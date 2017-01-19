package web.models;

import java.io.Serializable;

/**
 * @author sergio
 */
public class SigninCredentials implements Serializable {
    
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SigninCredentials{" + "username=" + username + ", password=" + password + '}';
    }
}
