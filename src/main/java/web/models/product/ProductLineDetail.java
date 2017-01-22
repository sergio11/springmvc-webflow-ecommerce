package web.models.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import persistence.models.ProductLine;
import persistence.models.Review;

/**
 *
 * @author sergio
 */
public class ProductLineDetail implements Serializable {
    
    private ProductLine productLine;
    private List<Review> reviews = new ArrayList();
    private Double ratingAvg;

    public ProductLineDetail(ProductLine productLine, List<Review> reviews, Double ratingAvg) {
        this.productLine = productLine;
        this.reviews = reviews;
        this.ratingAvg = ratingAvg;
    }

    public ProductLine getProductLine() {
        return productLine;
    }

    public void setProductLine(ProductLine productLine) {
        this.productLine = productLine;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Double getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(Double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }
    
}
