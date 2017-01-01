package persistence.specifications;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import persistence.models.Order;
import persistence.models.Order_;
import web.models.FilterOrder;

/**
 *
 * @author sergio
 */
public class OrderFilterSpecification implements Specification<Order> {

    private FilterOrder filterOrder;

    public OrderFilterSpecification(FilterOrder filterOrder) {
        this.filterOrder = filterOrder;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        // filter by order id
        if (filterOrder.getId() != null) {
            predicates.add(cb.equal(root.get(Order_.id), filterOrder.getId()));
        }
        // filter by purchased on date from
        if(filterOrder.getDateFrom() != null){
            predicates.add(cb.greaterThanOrEqualTo(root.get(Order_.purchasedOn), filterOrder.getDateFrom()));
        }
        // filter by purchased on date to
        if(filterOrder.getDateTo() != null){
            predicates.add(cb.lessThanOrEqualTo(root.get(Order_.purchasedOn), filterOrder.getDateTo()));
        }
        // filter by customer name
        if(filterOrder.getCustomer() != null && !filterOrder.getCustomer().isEmpty()){
            predicates.add(cb.like(root.get(Order_.customer).<String>get(Order_.customer.getName()), "%" + filterOrder.getCustomer() + "%"));
        }
        // filter by basePriceFrom 
        if (filterOrder.getBasePriceFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Order_.basePrice), filterOrder.getBasePriceFrom()));
        }
        // filter by basePriceTo
        if (filterOrder.getBasePriceTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Order_.basePrice), filterOrder.getBasePriceTo()));
        }
        // filter by purchasedPriceFrom 
        if (filterOrder.getPurchasedPriceFrom()!= null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Order_.purchasedPrice), filterOrder.getPurchasedPriceFrom()));
        }
        // filter by purchasedPriceTo
        if (filterOrder.getPurchasedPriceTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Order_.purchasedPrice), filterOrder.getPurchasedPriceTo()));
        }
        // filter by status
        if (filterOrder.getStatus() != null) {
            predicates.add(cb.equal(root.get(Order_.status), filterOrder.getStatus()));
        }
        return andTogether(predicates, cb);
    }

    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
