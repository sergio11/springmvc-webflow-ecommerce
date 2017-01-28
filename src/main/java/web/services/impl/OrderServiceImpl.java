package web.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.models.OrderStatusEnum;
import persistence.repositories.OrderRepository;
import web.services.OrderService;

/**
 * @author sergio
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Long getNewOrders() {
        return orderRepository.countByStatus(OrderStatusEnum.PENDING);
    }

    @Override
    public Double getTotalProfit() {
        return orderRepository.getTotalProfit();
    }
}
