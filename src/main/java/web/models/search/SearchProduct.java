package web.models.search;

import java.io.Serializable;
import persistence.models.StockAvailabilityEnum;

/**
 * @author sergio
 */
public class SearchProduct implements Serializable {
    
    private String query;
    private StockAvailabilityEnum avaliability;
    private Double priceMin;
    private Double priceMax;
    private ProductSortEnum sort;
    private Integer limit = 20;

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

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }
    
    public ProductSortEnum getSort() {
        return sort;
    }

    public void setSort(ProductSortEnum sort) {
        this.sort = sort;
    }
    
    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
