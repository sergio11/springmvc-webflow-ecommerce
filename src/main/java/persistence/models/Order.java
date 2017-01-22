package persistence.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * @author sergio
 */
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
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
    private Set<OrderLine> orderLines = new HashSet();
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Address shipTo;
    @ManyToOne(fetch = FetchType.EAGER)
    private Address billTo;
    

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
        customer.addOrder(this);
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

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
    
    public void addOrderLine(OrderLine orderLine) {
        if(!orderLines.contains(orderLine)) {
            orderLines.add(orderLine);
            orderLine.setOrder(this);
        }
    }

    public Address getShipTo() {
        return shipTo;
    }

    public void setShipTo(Address shipTo) {
        this.shipTo = shipTo;
    }

    public Address getBillTo() {
        return billTo;
    }

    public void setBillTo(Address billTo) {
        this.billTo = billTo;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", customer=" + customer + ", purchasedOn=" + purchasedOn + ", basePrice=" + basePrice + ", purchasedPrice=" + purchasedPrice + ", status=" + status + ", orderLines=" + orderLines + '}';
    }
}
