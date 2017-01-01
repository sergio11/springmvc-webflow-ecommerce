package web.models;

import java.io.Serializable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

/**
 * @author sergio
 */
public class DataTableUserInput extends DataTablesInput implements Serializable {
    
    private FilterUser filterUser = new FilterUser();

    public FilterUser getFilterUser() {
        return filterUser;
    }

    public void setFilterUser(FilterUser filterUser) {
        this.filterUser = filterUser;
    }
}
