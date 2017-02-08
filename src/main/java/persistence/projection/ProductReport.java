package persistence.projection;

import java.util.Date;

import persistence.models.ProductStatusEnum;

public interface ProductReport {
	Long getId();
	String getName();
	Double getPrice();
	Date getCreateAt();
	ProductStatusEnum getStatus();
}
