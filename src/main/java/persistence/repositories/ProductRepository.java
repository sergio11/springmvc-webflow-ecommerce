package persistence.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import persistence.models.Product;

/**
 * @author sergio
 */
public interface ProductRepository extends DataTablesRepository<Product, Long> {
    List<Product> findFirst10ByOrderByCreateAtDesc();
    Page<Product> findByNameIgnoreCaseContaining(String name, Pageable pageable);
    @Query(value = "SELECT p FROM Product p LEFT JOIN p.reviews r GROUP BY p ORDER BY AVG(r.rating) DESC")
    Page<Product> findFeaturedProducts(Pageable pageable);
    @Query(value = " SELECT p FROM Product p "
            + " JOIN p.productLines pl "
            + " LEFT JOIN pl.orderLines ol "
            + " GROUP BY p ORDER BY COUNT(p.id) DESC ")
    Page<Product> findBestsellersProducts(Pageable pageable);
}
