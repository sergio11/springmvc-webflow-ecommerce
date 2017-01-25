package persistence.models;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * @author sergio
 */
@Entity
@Table(name = "ORDER_LINES")
public class OrderLine implements Serializable {
    
    @EmbeddedId
    private OrderLineId orderLineId;
    @MapsId("orderId") 
    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    private Order order;
    @MapsId("productLineId")
    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    private ProductLine productLine;
    @Min(value = 1, message = "{order.line.quantity.min}")
    @Max(value = 999, message = "{order.line.quantity.max}")
    @Column(nullable = false)
    private Integer quantity;
    private Double totalPrice;
    private Integer discount;
    
    public OrderLine(){}

    public OrderLine(Order order, ProductLine productLine, Integer quantity, Double totalPrice, Integer discount) {
        this.orderLineId = new OrderLineId(order.getId(), productLine.getId());
        this.order = order;
        this.productLine = productLine;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }

    public OrderLineId getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(OrderLineId orderLineId) {
        this.orderLineId = orderLineId;
    }
    
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
        productLine.addOrderLine(this);
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
