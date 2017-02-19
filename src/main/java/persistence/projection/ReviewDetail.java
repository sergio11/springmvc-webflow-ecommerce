package persistence.projection;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author sergio
 */
public interface ReviewDetail {

    Date getCreateAt();

    Float getRating();

    String getBody();
    
    @Value("#{target.user.fullName}")
    String getUsername();
}
