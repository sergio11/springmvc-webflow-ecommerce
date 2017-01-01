package web.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sergio
 */
public class FilterUser implements Serializable {
    
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private Date lastLoginAccessFrom;
    private Date lastLoginAccessTo;
    private Boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getLastLoginAccessFrom() {
        return lastLoginAccessFrom;
    }

    public void setLastLoginAccessFrom(Date lastLoginAccessFrom) {
        this.lastLoginAccessFrom = lastLoginAccessFrom;
    }

    public Date getLastLoginAccessTo() {
        return lastLoginAccessTo;
    }

    public void setLastLoginAccessTo(Date lastLoginAccessTo) {
        this.lastLoginAccessTo = lastLoginAccessTo;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
