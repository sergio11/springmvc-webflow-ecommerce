package web.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import persistence.models.Product;
import persistence.models.Review;
import persistence.models.ReviewStatusEnum;
import persistence.repositories.ProductRepository;
import persistence.repositories.ReviewRepository;
import persistence.specifications.SearchProductSpecification;
import web.models.search.SearchProduct;
import web.services.ProductService;
import web.utils.ProductUtils;

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
    public Page<Product> search(String query) {
        return productRepository.findByNameIgnoreCaseContaining(query, new PageRequest(0, 20));
    }

    @Override
    public Page<Product> search(SearchProduct searchProduct) {
        return productRepository.findAll(
                new SearchProductSpecification(searchProduct), 
                new PageRequest(0, searchProduct.getLimit(), 
                ProductUtils.getProductOrder(searchProduct.getSort())));
    }

    @Override
    public Page<Product> search(SearchProduct searchProduct, Integer page) {
        return productRepository.findAll(
                new SearchProductSpecification(searchProduct), 
                new PageRequest(page, searchProduct.getLimit(),
                ProductUtils.getProductOrder(searchProduct.getSort())));
    }

    @Override
    public List<Product> getThreeFeaturedProducts() {
        Page<Product> pageProducts = productRepository.findFeaturedProducts(new PageRequest(0, 3));
        return pageProducts.getContent();
    }

    @Override
    public List<Product> getTwoBestsellersProducts() {
        Page<Product> pageProducts = productRepository.findBestsellersProducts(new PageRequest(0, 2));
        return pageProducts.getContent();
    }
}
