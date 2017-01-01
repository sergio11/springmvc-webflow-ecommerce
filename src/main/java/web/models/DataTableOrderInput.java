package web.models;

import java.io.Serializable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

/**
 *
 * @author sergio
 */
public class DataTableOrderInput extends DataTablesInput implements Serializable {
    
    private FilterOrder filterOrder;

    public FilterOrder getFilterOrder() {
        return filterOrder;
    }

    public void setFilterOrder(FilterOrder filterOrder) {
        this.filterOrder = filterOrder;
    }
}
