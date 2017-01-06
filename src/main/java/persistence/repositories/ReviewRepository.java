package persistence.repositories;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import persistence.models.Review;
import persistence.models.ReviewStatusEnum;

/**
 * @author sergio
 */
public interface ReviewRepository extends DataTablesRepository<Review, Long> {
    int countByProductIdAndStatus(Long id, ReviewStatusEnum status);
}
