package web.events.user;

import java.io.Serializable;


/**
 * @author sergio
 */
public class ChangePasswordEvent implements Serializable {
    
    private String username;

    public ChangePasswordEvent(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
