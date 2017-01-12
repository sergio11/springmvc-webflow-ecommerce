package web.services;

import java.util.List;
import persistence.models.Product;


/**
 * @author sergio
 */
public interface ProductService {
    List<Product> getNewArrivals();
}
