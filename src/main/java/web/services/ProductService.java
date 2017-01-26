package web.services;

import java.util.List;
import org.springframework.data.domain.Page;
import persistence.models.Product;
import persistence.models.Review;
import web.models.product.ProductLineDetail;
import web.models.search.SearchProduct;


/**
 * @author sergio
 */
public interface ProductService {
    List<Product> getNewArrivals();
    List<Product> getThreeFeaturedProducts();
    List<Product> getTwoBestsellersProducts();
    List<Review> getApprovedReviews(Long productId);
    Page<Product> search(String query);
    Page<Product> search(SearchProduct searchProduct);
    Page<Product> search(SearchProduct searchProduct, Integer page);
    ProductLineDetail getProductLineDetail(Long id);
}
