package web.frontend.controllers;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import persistence.models.Product;
import persistence.models.Review;
import persistence.models.User;
import persistence.repositories.ProductRepository;
import persistence.repositories.ReviewRepository;
import security.CurrentUserAttached;

/**
 * @author sergio
 */
@Controller("FrontendReviewsController")
@RequestMapping("/products/{productId}/reviews")
@SessionAttributes({ReviewController.ATTRIBUTE_NAME})
public class ReviewController {
    
    private static Logger logger = LoggerFactory.getLogger(ReviewController.class);
    
    public static final String ATTRIBUTE_NAME = "review";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @GetMapping("/create")
    public String create(@PathVariable Long productId, Model model) {
        if (!model.containsAttribute(BINDING_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME,  new Review());
        }
        String url = "/products/"+productId+"/reviews/create";
        return String.format("frontend/fragments/product/review::create(url='%s')",url);
    }
    
    @PostMapping("/create")
    public String create(
            @PathVariable Long productId,
            @ModelAttribute(ATTRIBUTE_NAME) @Valid Review review, 
            BindingResult bindingResult,
            RedirectAttributes model,
            // SessionStatus lets you clear your SessionAttributes
            SessionStatus sessionStatus,
            @CurrentUserAttached User currentUser
    ){
        model.addAttribute("productId", productId);
        String urlRedirect = "redirect:/products/{productId}/reviews/create";
        if (bindingResult.hasErrors()) {
            model.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            return urlRedirect;
        }
        Product product = productRepository.findOne(productId);
        review.setProduct(product);
        review.setUser(currentUser);
        logger.info("Review for product: " + product);
        reviewRepository.save(review);
        sessionStatus.setComplete(); //remove product from session
        return urlRedirect;
    }
}
