package web.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.services.ProductService;

/**
 * @author sergio
 */
@Controller("FrontendHomeController")
public class HomeController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping(value={"/home", "/"})
    public String index(Model model){
        // add new arrivals products
        model.addAttribute("arrivals", productService.getNewArrivals());
        // add featured products
        model.addAttribute("featured", productService.getNewArrivals());
        // add bestsellers products
        model.addAttribute("bestsellers", productService.getNewArrivals());
        return "frontend/index";
    }
}
