package web.admin.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.validation.annotation.Validated;
import persistence.models.Product;
import persistence.repositories.ProductRepository;
import web.admin.exceptions.ProductNotFoundException;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.models.ProductLine;
import persistence.models.ReviewStatusEnum;
import persistence.projection.ProductReport;
import persistence.repositories.ReviewRepository;
import web.admin.exceptions.ProductsNotFoundException;

/**
 * @author sergio
 */
@Controller("AdminProductController")
@RequestMapping("/admin/products")
@SessionAttributes({ProductController.ATTRIBUTE_NAME})
@Validated
public class ProductController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    public static final String ATTRIBUTE_NAME = "product";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;

    @Autowired
    private ProductRepository productsRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @GetMapping(value = {"/all", "/"})
    public String all(Model model) {
        model.addAttribute("products", new ArrayList<Product>());
        return "admin/dashboard/product/all";
    }

    @GetMapping("/report")
    public ModelAndView report(
            @Pattern(regexp = "pdf|excel") @RequestParam("format") String format,
            ModelMap modelMap,
            ModelAndView modelAndView) {
        List<ProductReport> products = productsRepository.findByOrderByCreateAt();
        if(products == null) {
            logger.info("No Products To Report Found");
            throw new ProductsNotFoundException();
        }
        logger.info("Product to report: " + products.size());
        JRBeanCollectionDataSource dataset = new JRBeanCollectionDataSource(products);
        modelMap.put("logo", ClassLoader.getSystemResourceAsStream("invoice_logo.png"));
        modelMap.put("format", format);
        modelMap.put("ProductsItemSource", dataset);
        modelAndView = new ModelAndView("rpt_all_products", modelMap);
        return modelAndView;
    }

    @GetMapping("/create")
    public String create(Model model) {
        if (!model.containsAttribute(BINDING_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME, new Product());
        }
        return "admin/dashboard/product/create";
    }

    @GetMapping("/edit/{productId}")
    public String edit(@PathVariable Long productId, Model model) {
        /* if "fresh" GET (ie, not redirect w validation errors): */
        if (!model.containsAttribute(BINDING_RESULT_NAME)) {
            Product product = productsRepository.findOne(productId);
            if (product == null) {
                throw new ProductNotFoundException();
            }
            logger.info("Product Lines count: " + product.getProductLines().size());
            model.addAttribute(ATTRIBUTE_NAME, product);
        }
        int newReviews = reviewRepository.countByProductIdAndStatus(productId, ReviewStatusEnum.PENDING);
        if (newReviews > 0) {
            model.addAttribute("newReviews", newReviews);
        }
        return "admin/dashboard/product/edit";
    }

    @PostMapping("/save")
    public String process(
            // Flash flash,
            @ModelAttribute(ATTRIBUTE_NAME) @Valid Product product,
            BindingResult bindingResult,
            @RequestParam(value = "continueEditing", required = false, defaultValue = "false") boolean continueEditing,
            RedirectAttributes model,
            // SessionStatus lets you clear your SessionAttributes
            SessionStatus sessionStatus,
            HttpServletRequest request) {

        for (ProductLine line : product.getProductLines()) {
            line.setProduct(product);
        }

        logger.info("Product to save: " + product.toString());
        if (bindingResult.hasErrors()) {
            model.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }

        productsRepository.save(product);
        sessionStatus.setComplete(); //remove product from session
        // add success flash message
        //flash.success("message.product.save.success", product.getId());
        String redirectTo;
        if (continueEditing) {
            model.addAttribute("productId", product.getId());
            redirectTo = "redirect:/admin/products/edit/{productId}";
        } else {
            redirectTo = "redirect:/admin/products/all";
        }
        return redirectTo;
    }
}
