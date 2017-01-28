package persistence.specifications;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.models.Product;
import persistence.models.ProductCategory_;
import persistence.models.Product_;
import web.models.search.SearchProduct;


/**
 * @author sergio
 */
public class SearchProductByCategorySpecification extends SearchProductSpecification {
    
    private static Logger logger = LoggerFactory.getLogger(SearchProductByCategorySpecification.class);

    private String slug;

    public SearchProductByCategorySpecification(SearchProduct searchProduct, String slug) {
        super(searchProduct);
        this.slug = slug;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        //super.toPredicate(root, cq, cb);
        List<Predicate> predicates = new ArrayList<>();
        logger.info("Product Slug: " + slug);
        if(slug != null && !slug.isEmpty())
            predicates.add(cb.equal(root.get(Product_.category).<String>get(ProductCategory_.slug.getName()), slug));
        return andTogether(predicates, cb);
    }
}
