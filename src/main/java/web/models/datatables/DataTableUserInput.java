package web.models.datatables;

import java.io.Serializable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

/**
 *
 * @author sergio
 */
public class DataTableUserInput extends DataTablesInput implements Serializable {
    
    private FilterUser filter = new FilterUser();

    public FilterUser getFilter() {
        return filter;
    }

    public void setFilter(FilterUser filter) {
        this.filter = filter;
    }
}
