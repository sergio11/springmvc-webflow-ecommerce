package persistence.projection;

import java.io.Serializable;

/**
 * @author sergio
 */
public class OrdersByCountry implements Serializable {
    
    private String code;
    private Long orders;

    public OrdersByCountry(String code, Long orders) {
        this.code = code;
        this.orders = orders;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrdersByCountry{" + "code=" + code + ", orders=" + orders + '}';
    }
}
