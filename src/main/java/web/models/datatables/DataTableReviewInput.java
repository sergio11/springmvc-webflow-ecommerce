package web.models.datatables;

import java.io.Serializable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

/**
 * @author sergio
 */
public class DataTableReviewInput extends DataTablesInput implements Serializable {
    
    private FilterReview filter = new FilterReview();

    public FilterReview getFilter() {
        return filter;
    }

    public void setFilter(FilterReview filter) {
        this.filter = filter;
    }
}
