package web.services;

import java.util.List;
import persistence.models.Product;
import persistence.models.Review;


/**
 * @author sergio
 */
public interface ProductService {
    List<Product> getNewArrivals();
    List<Review> getApprovedReviews(Long productId);
    List<Product> search(String query);
}
