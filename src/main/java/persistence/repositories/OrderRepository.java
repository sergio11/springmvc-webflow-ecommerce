package persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import persistence.models.Order;
import persistence.models.OrderStatusEnum;
import persistence.projection.OrdersByCountry;

/**
 * @author sergio
 */
public interface OrderRepository extends DataTablesRepository<Order, Long> {
    long countByStatus(OrderStatusEnum status);
    long countByCustomerId(Long id);
    @Query(value = "SELECT SUM(o.purchasedPrice) FROM Order o WHERE o.customer.id = :customer")
    Double getTotalSpentByUser(@Param("customer") Long id);
    @Query(value = "SELECT SUM(o.purchasedPrice) FROM Order o WHERE o.customer.id = :customer AND MONTH(o.purchasedOn) = :month")
    Double getTotalSpentByUserAndMonth(@Param("customer") Long id, @Param("month") Integer month);
    @Query( nativeQuery = true, value = "SELECT SUM(purchasedPrice) FROM ORDERS")
    Double getTotalProfit();
    @Query(value = "SELECT new persistence.projection.OrdersByCountry(co.code, COUNT(co)) "
            + " FROM Order o JOIN o.customer c JOIN c.country co GROUP BY co.code")
    List<OrdersByCountry> getOrdersByCountry();
}
