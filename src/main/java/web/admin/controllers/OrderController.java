package web.admin.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.repositories.OrderRepository;

/**
 *
 * @author sergio
 */
@Controller("AdminOrderController")
@RequestMapping("/admin/orders")
public class OrderController {
    
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
    
    @Autowired
    private OrderRepository orderRepository;
    
    @GetMapping("/all")
    public String all(Model model){
        Long ordersCount = orderRepository.count();
        model.addAttribute("ordersCount",  ordersCount);
        return "admin/dashboard/order/all";
    }
            
    
}
