package persistence.repositories;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import persistence.models.Order;
import persistence.models.OrderStatusEnum;

/**
 * @author sergio
 */
public interface OrderRepository extends DataTablesRepository<Order, Long> {
    long countByStatus(OrderStatusEnum status);
    @Query( nativeQuery = true, value = "SELECT SUM(purchasedPrice) FROM ORDERS")
    Double getTotalProfit();
}
