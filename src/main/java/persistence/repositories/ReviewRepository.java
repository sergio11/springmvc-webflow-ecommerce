package persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import persistence.models.Review;
import persistence.models.ReviewStatusEnum;

/**
 * @author sergio
 */
public interface ReviewRepository extends DataTablesRepository<Review, Long> {
    int countByProductIdAndStatus(Long id, ReviewStatusEnum status);
    List<Review> findByProductId(Long id);
    List<Review> findByProductIdAndStatus(Long id, ReviewStatusEnum status);
    @Query(value = "SELECT AVG(r.rating) FROM Review r LEFT JOIN r.product p WHERE p.id = :id GROUP BY p")
    Double getRatingAvgByProduct(@Param("id") Long id);
}
