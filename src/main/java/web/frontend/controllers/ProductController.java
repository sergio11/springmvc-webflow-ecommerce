package web.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.models.ProductLine;
import persistence.repositories.ProductLineRepository;


/**
 * @author sergio
 */
@Controller("FrontendProductController")
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductLineRepository productLineRepository;
    
    @GetMapping("/detail/{line}")
    public String detail(@PathVariable Long line, Model model){
        ProductLine productLine = productLineRepository.findOne(line);
        model.addAttribute("productLine", productLine);
        return "frontend/product/detail";
    }
}
