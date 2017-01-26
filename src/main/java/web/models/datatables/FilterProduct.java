package web.models.datatables;

import java.io.Serializable;
import persistence.models.ProductStatusEnum;

/**
 *
 * @author sergio
 */
public class FilterProduct implements Serializable {
    
    private Long id;
    private String name;
    private Double priceFrom;
    private Double priceTo;
    private ProductStatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Double priceTo) {
        this.priceTo = priceTo;
    }

    public ProductStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProductStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FilterProduct{" + "id=" + id + ", name=" + name + ", priceFrom=" + priceFrom + ", priceTo=" + priceTo + ", status=" + status + '}';
    }

    
}
