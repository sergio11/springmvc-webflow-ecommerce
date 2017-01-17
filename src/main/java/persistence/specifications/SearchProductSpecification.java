package persistence.specifications;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import persistence.models.Product;
import persistence.models.Product_;
import web.models.search.SearchProduct;

/**
 *
 * @author sergio
 */
public class SearchProductSpecification implements Specification<Product> {

    private SearchProduct searchProduct;

    public SearchProductSpecification(SearchProduct searchProduct) {
        this.searchProduct = searchProduct;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        
        // filter by name
        if (searchProduct.getQuery() != null && !searchProduct.getQuery().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get(Product_.name)), "%" + searchProduct.getQuery().toLowerCase() + "%"));
        }
        return andTogether(predicates, cb);
    }

    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
