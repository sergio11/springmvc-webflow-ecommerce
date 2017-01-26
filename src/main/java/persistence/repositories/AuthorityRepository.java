package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.Authority;
import persistence.models.AuthorityEnum;

/**
 *
 * @author sergio
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByType(AuthorityEnum type);
}
