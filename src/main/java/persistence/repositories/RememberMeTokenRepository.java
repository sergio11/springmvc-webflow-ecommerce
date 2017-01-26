package persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.RememberMeToken;

/**
 * @author sergio
 */
public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, Long> {
    RememberMeToken findBySeries(String series);
    List<RememberMeToken> findByUsername(String username);
}
