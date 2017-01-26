package persistence.specifications;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import persistence.models.User;
import persistence.models.User_;
import web.models.datatables.FilterUser;

/**
 *
 * @author sergio
 */
public class UserFilterSpecification implements Specification<User> {

    private FilterUser filterUser;

    public UserFilterSpecification(FilterUser filterUser) {
        this.filterUser = filterUser;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        // filter by user id
        if (filterUser.getId() != null) {
            predicates.add(cb.equal(root.get(User_.id), filterUser.getId()));
        }
        // filter by username
        if(filterUser.getUsername() != null && !filterUser.getUsername().isEmpty()){
            predicates.add(cb.like(cb.lower(root.get(User_.username)), "%" + filterUser.getUsername().toLowerCase() + "%"));
        }
        // filter by fullname
        if(filterUser.getFullname()!= null && !filterUser.getFullname().isEmpty()){
            predicates.add(cb.like(cb.lower(root.get(User_.fullName)), "%" + filterUser.getFullname().toLowerCase() + "%"));
        }
        // filter by last access from
        if(filterUser.getLastLoginAccessFrom() != null){
            predicates.add(cb.greaterThanOrEqualTo(root.get(User_.lastLoginAccess), filterUser.getLastLoginAccessFrom()));
        }
        // filter by last access to
        if(filterUser.getLastLoginAccessTo()!= null){
            predicates.add(cb.lessThanOrEqualTo(root.get(User_.lastLoginAccess), filterUser.getLastLoginAccessTo()));
        }
        // exclude current user
        if(filterUser.getCurrentUser() != null){
            predicates.add(cb.notEqual(root.get(User_.id), filterUser.getCurrentUser()));
        }
        return andTogether(predicates, cb);
    }

    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
