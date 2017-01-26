package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.ProductCategory;

/**
 * @author sergio
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
   ProductCategory findBySlug(String slug);
}
