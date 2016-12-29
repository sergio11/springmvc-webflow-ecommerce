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
public class ProductFilterSpecification implements Specification<Product>{
   
    private FilterProduct filterProduct;

    public ProductFilterSpecification(FilterProduct filterProduct) {
        this.filterProduct = filterProduct;
    }
   
    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        // filter for product price from
        if(filterProduct.getPriceFrom() != null){
            predicates.add(cb.greaterThanOrEqualTo(root.get(Product_.price), filterProduct.getPriceFrom()));
        }
        // filter for product price to
        if(filterProduct.getPriceTo() != null){
            predicates.add(cb.lessThanOrEqualTo(root.get(Product_.price), filterProduct.getPriceTo()));
        }
        return andTogether(predicates, cb);
    }
    
    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[0]));
      }
}
