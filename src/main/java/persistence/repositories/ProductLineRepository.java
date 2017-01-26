package persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.ProductLine;
import persistence.projection.ProductLineView;


/**
 * @author sergio
 */
public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {
    List<ProductLineView> findByIdNotAndStockGreaterThan(Long id, Integer stock);
    List<ProductLine> findByProductId(Long id);
    List<ProductLine> findByProductName(String name);
}
