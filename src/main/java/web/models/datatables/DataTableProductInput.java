package web.models.datatables;

import java.io.Serializable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

/**
 *
 * @author sergio
 */
public class DataTableProductInput extends DataTablesInput implements Serializable {
    
    private FilterProduct filter = new FilterProduct();

    public FilterProduct getFilter() {
        return filter;
    }

    public void setFilter(FilterProduct filter) {
        this.filter = filter;
    }
}
