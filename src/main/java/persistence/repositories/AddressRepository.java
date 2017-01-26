package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.Address;

/**
 * @author sergio
 */
public interface AddressRepository extends JpaRepository<Address, Long> {}
