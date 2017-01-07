package persistence.repositories;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import persistence.models.Product;

/**
 * @author sergio
 */
public interface ProductRepository extends DataTablesRepository<Product, Long> {}
