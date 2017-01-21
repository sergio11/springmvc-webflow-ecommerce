package web.frontend.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import persistence.models.Product;
import web.services.ProductService;

/**
 * @author sergio
 */
@Controller("FrontendHomeController")
public class HomeController {
    
    private static Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    private ProductService productService;
    
    @GetMapping(value={"/home", "/"})
    public String index(Model model){
        List<Product> arrivals = productService.getNewArrivals();
        logger.info("Arrivals : " + arrivals.size());
        logger.info("Arrivals : " + arrivals.get(0).toString());
        // add new arrivals products
        model.addAttribute("arrivals", productService.getNewArrivals());
        // add featured products
        model.addAttribute("featured", productService.getNewArrivals());
        // add bestsellers products
        model.addAttribute("bestsellers", productService.getNewArrivals());
        return "frontend/index";
    }
}
