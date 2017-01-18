package persistence.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import persistence.models.Product;

/**
 * @author sergio
 */
public interface ProductRepository extends DataTablesRepository<Product, Long> {
    List<Product> findFirst10ByOrderByCreateAtDesc();
    Page<Product> findByNameIgnoreCaseContaining(String name, Pageable pageable);
}
