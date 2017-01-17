package web.services;

import java.util.List;
import persistence.models.Product;
import persistence.models.Review;
import web.models.search.SearchProduct;


/**
 * @author sergio
 */
public interface ProductService {
    List<Product> getNewArrivals();
    List<Review> getApprovedReviews(Long productId);
    List<Product> search(String query);
    List<Product> search(SearchProduct searchProduct);
}
