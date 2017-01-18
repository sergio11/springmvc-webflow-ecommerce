package web.frontend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import persistence.models.Product;
import persistence.models.ProductLine;
import persistence.models.Review;
import persistence.repositories.ProductLineRepository;
import web.frontend.exceptions.ProductLineNotFoundException;
import web.models.search.SearchProduct;
import web.services.ProductService;

/**
 * @author sergio
 */
@Controller("FrontendProductController")
@RequestMapping("/products")
@SessionAttributes({ProductController.SEARCH_PRODUCT})
public class ProductController {
    
    private static Logger logger = LoggerFactory.getLogger(web.admin.controllers.ProductController.class);
    
    public static final String SEARCH_PRODUCT = "searchProduct";
    public static final String BINDING_SEARCH_PRODUCT = "org.springframework.validation.BindingResult." + SEARCH_PRODUCT;
    
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
    public String search(@RequestParam(value="query", required = true) String query, RedirectAttributes model){
        SearchProduct searchProduct = new SearchProduct();
        searchProduct.setQuery(query);
        Page<Product> productPage = productService.search(query);
        model.addFlashAttribute(SEARCH_PRODUCT, searchProduct);
        model.addFlashAttribute("productPage", productPage);
        return "redirect:/products/search-result";
    }
    
    @PostMapping("/search")
    public String search(
            @ModelAttribute(SEARCH_PRODUCT) @Valid SearchProduct searchProduct, 
            BindingResult bindingResult,
            RedirectAttributes model,
            SessionStatus sessionStatus){
        
        String url = "redirect:/products/search-result";
        if(bindingResult.hasErrors()){
            model.addFlashAttribute(BINDING_SEARCH_PRODUCT, bindingResult);
            return url;
        }
        Page<Product> productPage = productService.search(searchProduct);
        logger.info("Total items: " + productPage.getContent().size());
        model.addFlashAttribute("productPage", productPage);
        return url;
    }
    
    @GetMapping("/search-result")
    public String result(Model model){
        model.addAttribute("bestsellers", new ArrayList<Product>());
        if(!model.containsAttribute(BINDING_SEARCH_PRODUCT) || !model.containsAttribute(SEARCH_PRODUCT)) {
            logger.info("Reset model: ");
            model.addAttribute(SEARCH_PRODUCT,  new SearchProduct());
        }
        return "frontend/product/search_result";
    }
    
    @GetMapping( value = { "/search-result/page/", "/search-result/page", "/search-result/page/{page}" } )
    public String result(
            @ModelAttribute(SEARCH_PRODUCT) SearchProduct searchProduct,
            @PathVariable Optional<Integer> page,
            Model model) {
        model.addAttribute("bestsellers", new ArrayList<Product>());
        Page<Product> productPage = productService.search(searchProduct, page.isPresent() ? page.get() : 0);
        model.addAttribute("productPage", productPage);
        return "frontend/product/search_result";
    }
}
