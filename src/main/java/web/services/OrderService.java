package web.services;

import persistence.models.Order;



/**
 * @author sergio
 */
public interface OrderService {
    Order create(Long consumer);
}
