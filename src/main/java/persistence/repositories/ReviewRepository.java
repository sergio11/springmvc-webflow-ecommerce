package persistence.repositories;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import persistence.models.Review;

/**
 * @author sergio
 */
public interface ReviewRepository extends DataTablesRepository<Review, Long> {}
