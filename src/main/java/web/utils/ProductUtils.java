package web.utils;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import persistence.models.Product_;
import web.models.search.ProductSortEnum;

/**
 * @author sergio
 */
public class ProductUtils {
    
    public final static Sort getProductOrder(ProductSortEnum productSort){
        Order order = null;
        switch(productSort){
            case NAME_ASC:
                order = new Order(Sort.Direction.ASC, Product_.name.getName());
                break;
            case NAME_DESC:
                order = new Order(Sort.Direction.DESC, Product_.name.getName());
                break;
            case PRICE_ASC:
                order = new Order(Sort.Direction.ASC, Product_.price.getName());
                break;
            case PRICE_DESC:
                order = new Order(Sort.Direction.DESC, Product_.price.getName());
                break;
            default:
                order = new Order(Sort.Direction.DESC, Product_.createAt.getName());
        }
        return new Sort(order);
    }
}
