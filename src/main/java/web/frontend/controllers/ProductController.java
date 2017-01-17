package web.frontend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.models.Product;
import persistence.models.ProductLine;
import persistence.models.Review;
import persistence.repositories.ProductLineRepository;
import web.frontend.exceptions.ProductLineNotFoundException;
import web.services.ProductService;

/**
 * @author sergio
 */
@Controller("FrontendProductController")
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductLineRepository productLineRepository;
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/detail/{line}")
    public String detail(@PathVariable Long line, Model model){
        ProductLine productLine = productLineRepository.findOne(line);
        if(productLine == null){
            throw new ProductLineNotFoundException();
        }
        List<Review> reviews = productService.getApprovedReviews(productLine.getProduct().getId());
        model.addAttribute("productLine", productLine);
        model.addAttribute("reviews", reviews);
        return "frontend/product/detail";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam(value="query", required = true) String query, Model model){
        List<Product> products = productService.search(query);
        model.addAttribute("bestsellers", products);
        model.addAttribute("results", products);
        return "frontend/product/search_result";
    }
}
