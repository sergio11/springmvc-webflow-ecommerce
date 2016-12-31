package persistence.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 *
 * @author sergio
 */
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User customer;
    @Column(nullable = false)
    private Date purchasedOn = new Date();
    @Column(nullable = false, precision=5, scale=2)
    @DecimalMax(value="999.99", inclusive=true, message="{order.baseprice.max}")
    @DecimalMin(value="00.00", message="{order.baseprice.min}")
    private Double basePrice;
    @Column(nullable = false, precision=5, scale=2)
    private Double purchasedPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;
    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines = new ArrayList();
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Date getPurchasedOn() {
        return purchasedOn;
    }

    public void setPurchasedOn(Date purchasedOn) {
        this.purchasedOn = purchasedOn;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(Double purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", customer=" + customer + ", purchasedOn=" + purchasedOn + ", basePrice=" + basePrice + ", purchasedPrice=" + purchasedPrice + ", status=" + status + ", orderLines=" + orderLines + '}';
    }
}
