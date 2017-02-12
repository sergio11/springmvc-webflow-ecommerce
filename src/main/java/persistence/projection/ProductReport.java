package persistence.projection;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

public interface ProductReport {
	Long getId();
	String getName();
	Double getPrice();
	Date getCreateAt();
        @Value("#{target.status.name()}")
	String getStatus();
}
