package web.services;

import java.util.List;
import org.springframework.data.domain.Page;
import persistence.models.Product;
import persistence.models.Review;
import web.models.search.SearchProduct;


/**
 * @author sergio
 */
public interface ProductService {
    List<Product> getNewArrivals();
    List<Review> getApprovedReviews(Long productId);
    Page<Product> search(String query);
    Page<Product> search(SearchProduct searchProduct);
    Page<Product> search(SearchProduct searchProduct, Integer page);
}
