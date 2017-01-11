package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.ProductLine;


/**
 * @author sergio
 */
public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {}
