package persistence.models;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * @author sergio
 */
@Entity
@Table(name = "ORDER_LINES")
@IdClass(OrderLineId.class)
public class OrderLine implements Serializable {
    
    @Id
    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Order order;
    @Id
    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private ProductLine productLine;
    @Min(value = 1, message = "{order.line.quantity.min}")
    @Max(value = 999, message = "{order.line.quantity.max}")
    @Column(nullable = false)
    private Integer quantity;
    private Double totalPrice;
    private Integer discount;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        order.addOrderLine(this);
    }

    public ProductLine getProductLine() {
        return productLine;
    }

    public void setProductLine(ProductLine productLine) {
        this.productLine = productLine;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderLine{" + "productLine=" + productLine + ", quantity=" + quantity + ", totalPrice=" + totalPrice + ", discount=" + discount + '}';
    }
}
