package persistence.specifications;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import persistence.models.Product_;
import persistence.models.Review;
import web.models.datatables.FilterReview;
import persistence.models.Review_;
import persistence.models.User_;

/**
 * @author sergio
 */
public class ReviewFilterSpecification implements Specification<Review> {

    private FilterReview filterReview;

    public ReviewFilterSpecification(FilterReview filterReview) {
        this.filterReview = filterReview;
    }

    @Override
    public Predicate toPredicate(Root<Review> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        // filter by review id
        if (filterReview.getId() != null) {
            predicates.add(cb.equal(root.get(Review_.id), filterReview.getId()));
        }
        // filter by createAt on date from
        if(filterReview.getDateFrom() != null){
            predicates.add(cb.greaterThanOrEqualTo(root.get(Review_.createAt), filterReview.getDateFrom()));
        }
        // filter by purchased on date to
        if(filterReview.getDateTo() != null){
            predicates.add(cb.lessThanOrEqualTo(root.get(Review_.createAt), filterReview.getDateTo()));
        }
        // filter by customer name
        if(filterReview.getCustumer()!= null && !filterReview.getCustumer().isEmpty()){
            predicates.add(cb.like(cb.lower(root.get(Review_.user).<String>get(User_.fullName)), "%" + filterReview.getCustumer().toLowerCase() + "%"));
        }
        // filter by rating
        if(filterReview.getRating() != null){
            predicates.add(cb.greaterThanOrEqualTo(root.get(Review_.rating), filterReview.getRating()));
        }
        // filter by status
        if (filterReview.getStatus() != null) {
            predicates.add(cb.equal(root.get(Review_.status), filterReview.getStatus()));
        }
        //filter by product
        if(filterReview.getProduct()!= null){
            predicates.add(cb.equal(root.get(Review_.product).<Long>get(Product_.id), filterReview.getProduct()));
        }
        return andTogether(predicates, cb);
    }

    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
