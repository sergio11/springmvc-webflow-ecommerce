package persistence.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author sergio
 */
@Embeddable
public class OrderLineId implements Serializable {
    private static final long serialVersionUID = -2834827403836993112L;
    
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "product_line_id")
    private Long productLineId;

    public OrderLineId(Long orderId, Long productLineId) {
        this.orderId = orderId;
        this.productLineId = productLineId;
    }
    
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductLineId() {
        return productLineId;
    }

    public void setProductLineId(Long productLineId) {
        this.productLineId = productLineId;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.orderId);
        hash = 43 * hash + Objects.hashCode(this.productLineId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderLineId other = (OrderLineId) obj;
        if (!Objects.equals(this.orderId, other.orderId)) {
            return false;
        }
        if (!Objects.equals(this.productLineId, other.productLineId)) {
            return false;
        }
        return true;
    }
}
