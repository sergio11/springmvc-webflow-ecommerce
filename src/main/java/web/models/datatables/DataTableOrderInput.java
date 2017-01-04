package web.models.datatables;

import java.io.Serializable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

/**
 * @author sergio
 */
public class DataTableOrderInput extends DataTablesInput implements Serializable {
    
    private FilterOrder filter = new FilterOrder();

    public FilterOrder getFilter() {
        return filter;
    }

    public void setFilter(FilterOrder filter) {
        this.filter = filter;
    }
}
