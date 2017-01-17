package web.models.search;

import java.io.Serializable;
import persistence.models.StockAvailabilityEnum;

/**
 * @author sergio
 */
public class SearchProduct implements Serializable {
    
    private String query;
    private StockAvailabilityEnum avaliability;
    private String amount;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public StockAvailabilityEnum getAvaliability() {
        return avaliability;
    }

    public void setAvaliability(StockAvailabilityEnum avaliability) {
        this.avaliability = avaliability;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
