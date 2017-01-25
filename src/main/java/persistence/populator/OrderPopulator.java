package persistence.populator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import persistence.models.OrderLine;
import persistence.models.OrderStatusEnum;
import persistence.models.Product;
import persistence.models.User;
import persistence.repositories.OrderRepository;
import persistence.repositories.ProductRepository;
import persistence.repositories.UserRepository;
import persistence.models.Address;
import persistence.models.ProductLine;

/**
 * @author sergio
 */
@Component
@Profile("development")
public class OrderPopulator implements Serializable {
    
    private static Logger logger = LoggerFactory.getLogger(OrderPopulator.class);

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired 
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Order(4)
    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void contextRefreshedEvent() {
        logger.info("SETUP ORDER INIT DATA");
       
        List<persistence.models.Order> orders = new ArrayList();
        
        User customer = userRepository.findByUsername("sergio11");
        
        persistence.models.Order order1 = new persistence.models.Order();
        order1.setCustomer(customer);
        order1.setBasePrice(150.0);
        order1.setPurchasedPrice(150.0 + 3.0);
        order1.setPurchasedOn(new Date());
        order1.setStatus(OrderStatusEnum.PENDING);
        Address address = customer.getAddresses().iterator().next();
        order1.setBillTo(address);
        order1.setShipTo(address);
        
        Product product = productRepository.findByName("Nike Air Max 2017");
        ProductLine line = product.getProductLines().get(0);
        logger.info("Product Line: " + line.toString());
        OrderLine orderLine = new OrderLine(order1, line, 2, product.getPrice() * 2, null);
        order1.addOrderLine(orderLine);
        orders.add(order1);
        
        try{
            orderRepository.deleteAll();
            orderRepository.save(orders);
        } catch(Exception e){
            logger.error(e.getMessage());
        }
    }
}
