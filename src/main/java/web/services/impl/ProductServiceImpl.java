package web.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.models.Product;
import persistence.models.Review;
import persistence.models.ReviewStatusEnum;
import persistence.repositories.ProductRepository;
import persistence.repositories.ReviewRepository;
import web.services.ProductService;

/**
 * @author sergio
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Override
    public List<Product> getNewArrivals() {
        return productRepository.findFirst10ByOrderByCreateAtDesc();
    }

    @Override
    public List<Review> getApprovedReviews(Long productId) {
        return reviewRepository.findByProductIdAndStatus(productId, ReviewStatusEnum.APPROVED);
    }

    @Override
    public List<Product> search(String query) {
        return productRepository.findByNameIgnoreCaseContaining(query);
    }
}
