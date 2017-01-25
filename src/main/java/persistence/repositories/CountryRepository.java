package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.Country;

/**
 * @author sergio
 */
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByCode(String code);
}
