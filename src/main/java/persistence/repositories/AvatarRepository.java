package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.Avatar;

/**
 * @author sergio
 */
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
