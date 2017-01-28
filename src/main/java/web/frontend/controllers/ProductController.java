package web.frontend.controllers;

import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import persistence.models.Product;
import web.frontend.exceptions.SearchSpecificationNotFoundException;
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
    public static final String PRODUCT_PAGE_RESULTS = "pageResults";
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/detail/{line}")
    public String detail(@PathVariable Long line, Model model){
        model.addAttribute("productLineDetail", productService.getProductLineDetail(line));
        return "frontend/product/detail";
    }
    
    @GetMapping("/search")
    public String search(
            @RequestParam(value="query", required = true) String query, 
            RedirectAttributes model){
        SearchProduct searchProduct = new SearchProduct();
        searchProduct.setQuery(query);
        Page<Product> productPage = productService.search(query);
        model.addFlashAttribute(SEARCH_PRODUCT, searchProduct);
        model.addFlashAttribute(PRODUCT_PAGE_RESULTS, productPage);
        return "redirect:/products/search-result";
    }
    
    @PostMapping("/search")
    public String search(
            @ModelAttribute(SEARCH_PRODUCT) @Valid SearchProduct searchProduct, 
            BindingResult bindingResult,
            RedirectAttributes model){
        String url = "redirect:/products/search-result";
        if(bindingResult.hasErrors()){
            model.addFlashAttribute(BINDING_SEARCH_PRODUCT, bindingResult);
            return url;
        }
        Page<Product> productPage = productService.search(searchProduct);
        model.addFlashAttribute(PRODUCT_PAGE_RESULTS, productPage);
        return url;
    }
    
    @GetMapping( value = { "/search-result" })
    public String result(
            @ModelAttribute(PRODUCT_PAGE_RESULTS) Optional<Page<Product>> pageResults,
            Model model) {
        if(!pageResults.isPresent())
            throw new SearchSpecificationNotFoundException();
        model.addAttribute("bestsellers", new ArrayList<Product>());
        return "frontend/product/list/search_result";
    }
    
    @GetMapping( value = { "/search-result/{page}" } )
    public String result(
            @ModelAttribute(SEARCH_PRODUCT) Optional<SearchProduct> searchProduct,
            @PathVariable Integer page,
            Model model) {
        
        SearchProduct searchProductModel = null;
        if(!searchProduct.isPresent()) {
            searchProductModel = new SearchProduct();
            model.addAttribute(SEARCH_PRODUCT, searchProduct);
        } else {
            searchProductModel = searchProduct.get();
        }
        Page<Product> productPage = productService.search(searchProductModel, page);
        
        model.addAttribute("bestsellers", new ArrayList<Product>());
        model.addAttribute(PRODUCT_PAGE_RESULTS, productPage);
        return "frontend/product/list/search_result";
    }
    
    @GetMapping( value = { "/categories/{category}", "/categories/{category}/", "/categories/{category}/{page}" })
    public String result(
            @ModelAttribute(SEARCH_PRODUCT) Optional<SearchProduct> searchProduct,
            @PathVariable String category,
            @PathVariable Optional<Integer> page,
            Model model) {
        SearchProduct searchProductModel = null;
        if(!searchProduct.isPresent()) {
            searchProductModel = new SearchProduct();
            model.addAttribute(SEARCH_PRODUCT, searchProduct);
        } else {
            searchProductModel = searchProduct.get();
        }
        Page<Product> productPage = productService.search(searchProductModel, page.isPresent() ? page.get() : 0, category);
        model.addAttribute(PRODUCT_PAGE_RESULTS, productPage);
        return "frontend/product/list/categories";
    }
    
    @PostMapping( value = { "/categories/{category}", "/categories/{category}/" })
    public String result(
        @ModelAttribute(SEARCH_PRODUCT) SearchProduct searchProduct,
        @PathVariable String category,
        Model model
    ) {
        Page<Product> productPage = productService.search(searchProduct, 0, category);
        model.addAttribute(PRODUCT_PAGE_RESULTS, productPage);
        return "frontend/product/list/categories";
    }
}
