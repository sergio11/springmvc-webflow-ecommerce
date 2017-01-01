package web.models;

import java.io.Serializable;
import java.util.Date;
import persistence.models.OrderStatusEnum;

/**
 *
 * @author sergio
 */
public class FilterOrder implements Serializable {
    
    private Long id;
    private Date dateFrom;
    private Date dateTo;
    private String customer;
    private Double basePriceFrom;
    private Double basePriceTo;
    private Double purchasedPriceFrom;
    private Double purchasedPriceTo;
    private OrderStatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Double getBasePriceFrom() {
        return basePriceFrom;
    }

    public void setBasePriceFrom(Double basePriceFrom) {
        this.basePriceFrom = basePriceFrom;
    }

    public Double getBasePriceTo() {
        return basePriceTo;
    }

    public void setBasePriceTo(Double basePriceTo) {
        this.basePriceTo = basePriceTo;
    }

    public Double getPurchasedPriceFrom() {
        return purchasedPriceFrom;
    }

    public void setPurchasedPriceFrom(Double purchasedPriceFrom) {
        this.purchasedPriceFrom = purchasedPriceFrom;
    }

    public Double getPurchasedPriceTo() {
        return purchasedPriceTo;
    }

    public void setPurchasedPriceTo(Double purchasedPriceTo) {
        this.purchasedPriceTo = purchasedPriceTo;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }
}
