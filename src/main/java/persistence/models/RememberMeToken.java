package persistence.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

/**
 * @author sergio
 */
@Entity
@Table(name = "REMEMBER_TOKENS")
public class RememberMeToken implements Serializable {
    
    @Id
    private String series;
    private String username;
    private String tokenValue;
    private Date date;
    
    public RememberMeToken(){}
    
    public RememberMeToken(PersistentRememberMeToken token){
        this.series = token.getSeries();
        this.username = token.getUsername();
        this.tokenValue = token.getTokenValue();
        this.date = token.getDate();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
