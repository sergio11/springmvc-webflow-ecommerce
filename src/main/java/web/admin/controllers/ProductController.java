package web.admin.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import persistence.models.Product;
import persistence.repositories.ProductRepository;
import web.admin.exceptions.ProductNotFoundException;

/**
 *
 * @author sergio
 */
@Controller("AdminProductController")
@RequestMapping("/admin/products")
@SessionAttributes({ProductController.ATTRIBUTE_NAME})
public class ProductController {
    
    private static Logger logger = LoggerFactory.getLogger(ProductController.class);
    
    public static final String ATTRIBUTE_NAME = "product";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;
    
    @Autowired
    private ProductRepository productsRepository;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    
    @InitBinder
    public void initBinder(final WebDataBinder binder){
      final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy"); 
      binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    @GetMapping("/all")
    public String all(Model model){
        model.addAttribute("products",  new ArrayList<Product>());
        return "admin/dashboard/products";
    }
    
    @GetMapping("/create")
    public String create(Model model){
        if(!model.containsAttribute(BINDING_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME,  new Product());
        }
        return "admin/dashboard/product_edit";
    }
    
    @GetMapping("/edit/{productId}")
    public String edit(@PathVariable Long productId, Model model) {
        /* if "fresh" GET (ie, not redirect w validation errors): */
        if(!model.containsAttribute(BINDING_RESULT_NAME)) {
            Product product = productsRepository.findOne(productId);
            if (product == null) {
                throw new ProductNotFoundException();
            }
            model.addAttribute(ATTRIBUTE_NAME, product);
        }
        return "admin/dashboard/product_edit";
    }
    
    @PostMapping("/save")
    public String process(
            @ModelAttribute(ATTRIBUTE_NAME) @Valid Product product, 
            BindingResult bindingResult,
            RedirectAttributes model,
            // SessionStatus lets you clear your SessionAttributes
            SessionStatus sessionStatus,
            HttpServletRequest request){
       
        logger.info("Product to save: " + product.toString());
        if (bindingResult.hasErrors()) {
            model.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            String referer = request.getHeader("Referer");
            return "redirect:"+referer;
        }
        productsRepository.save(product);
        sessionStatus.setComplete(); //remove product from session
        List<String> successMessages = new ArrayList();
        successMessages.add(messageSource.getMessage("message.product.save.success", new Object[]{product.getId()}, Locale.getDefault()));
        model.addFlashAttribute("successFlashMessages", successMessages);
        return "redirect:/admin/products/all";
    }
}
