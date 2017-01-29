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
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(super.toPredicate(root, cq, cb));
        if(slug != null && !slug.isEmpty())
            predicates.add(cb.equal(root.get(Product_.category).get(ProductCategory_.slug), slug));
        return andTogether(predicates, cb);
    }
}
