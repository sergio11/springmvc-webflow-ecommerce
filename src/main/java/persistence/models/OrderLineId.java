package persistence.models;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author sergio
 */
public class OrderLineId implements Serializable {
    private static final long serialVersionUID = -2834827403836993112L;
    
    private Order order;
    private ProductLine productLine;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.order);
        hash = 67 * hash + Objects.hashCode(this.productLine);
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
        if (!Objects.equals(this.order, other.order)) {
            return false;
        }
        if (!Objects.equals(this.productLine, other.productLine)) {
            return false;
        }
        return true;
    }
    
    
}
