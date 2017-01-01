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
import web.models.FilterProduct;

/**
 *
 * @author sergio
 */
public class ProductFilterSpecification implements Specification<Product> {

    private FilterProduct filterProduct;

    public ProductFilterSpecification(FilterProduct filterProduct) {
        this.filterProduct = filterProduct;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        // filter by product id
        if (filterProduct.getId() != null) {
            predicates.add(cb.equal(root.get(Product_.id), filterProduct.getId()));
        }
        // filter by product price from
        if (filterProduct.getPriceFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Product_.price), filterProduct.getPriceFrom()));
        }
        // filter by product price to
        if (filterProduct.getPriceTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Product_.price), filterProduct.getPriceTo()));
        }
        // filter by name
        if (filterProduct.getName() != null && !filterProduct.getName().isEmpty()) {
            predicates.add(cb.like(root.get(Product_.name), "%" + filterProduct.getName() + "%"));
        }
        // filter by status
        if (filterProduct.getStatus() != null) {
            predicates.add(cb.equal(root.get(Product_.status), filterProduct.getStatus()));
        }
        return andTogether(predicates, cb);
    }

    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
