package persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import persistence.models.Product;

/**
 * @author sergio
 */
public interface ProductRepository extends DataTablesRepository<Product, Long> {
    List<Product> findFirst10ByOrderByCreateAtDesc();
    List<Product> findByNameIgnoreCaseContaining(String name);
}
