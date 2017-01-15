package web.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.models.Order;
import persistence.models.User;
import persistence.repositories.UserRepository;
import web.services.OrderService;

/**
 *
 * @author sergio
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public Order create(Long consumer) {
        User user = userRepository.findOne(consumer);
        Order order = new Order();
        order.setCustomer(user);
        return order;
    }
    
}
