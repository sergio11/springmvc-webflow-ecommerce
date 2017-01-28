package web.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.services.OrderService;
import web.services.ProductService;

/**
 *
 * @author sergio
 */
@Controller("AdminHomeController")
public class HomeController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/admin/home")
    public String show(Model model){
        // add new feedbacks
        model.addAttribute("feedbacks", productService.getNewFeedbacks());
        // add new orders
        model.addAttribute("newOrders", orderService.getNewOrders());
        // add total profit
        model.addAttribute("totalProfit", orderService.getTotalProfit());
        return "admin/dashboard/index";
    }
}
