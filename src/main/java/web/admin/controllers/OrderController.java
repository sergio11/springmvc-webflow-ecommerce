package web.admin.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import persistence.models.Order;
import persistence.repositories.OrderRepository;
import web.admin.exceptions.OrderNotFoundException;

/**
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
    
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Order order = orderRepository.findOne(id);
        if(order == null)
            throw new OrderNotFoundException();
        model.addAttribute("order", order);
        return "admin/dashboard/order/show";
    }
    
    
    @GetMapping("/report/{1}")
    public ModelAndView report(ModelMap modelMap, ModelAndView modelAndView) {
        //modelMap.put("datasource", getWidgets());
        modelMap.put("format", "pdf");
        modelAndView = new ModelAndView("rpt_order_detail", modelMap);
        return modelAndView;
    }
    
}
