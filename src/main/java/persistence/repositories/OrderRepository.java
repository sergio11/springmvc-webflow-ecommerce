package persistence.repositories;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import persistence.models.Order;

/**
 * @author sergio
 */
public interface OrderRepository extends DataTablesRepository<Order, Long> {}
