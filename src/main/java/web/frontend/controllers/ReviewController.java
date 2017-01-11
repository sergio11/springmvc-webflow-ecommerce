package web.frontend.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import persistence.models.Review;
import persistence.repositories.ReviewRepository;

/**
 * @author sergio
 */
@Controller("FrontendReviewsController")
@RequestMapping("/reviews")
@SessionAttributes({ReviewController.ATTRIBUTE_NAME})
public class ReviewController {
    
    private static Logger logger = LoggerFactory.getLogger(ReviewController.class);
    
    public static final String ATTRIBUTE_NAME = "review";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @GetMapping("/list/{product}")
    public String add(@PathVariable Long product, Model model) {
        List<Review> items = reviewRepository.findByProductId(product);
        if (!model.containsAttribute(BINDING_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME, new Review());
        }
        model.addAttribute("items", items);
        return "frontend/fragments/product/review::list";
    }
}
