package web.frontend.controllers;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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
import persistence.models.ProductLine;
import persistence.repositories.ProductLineRepository;
import web.models.shop.CartItem;
import web.services.CartService;

/**
 * @author sergio
 */
@Controller
@RequestMapping("/shopping-cart")
@SessionAttributes({CartController.ATTRIBUTE_NAME})
public class CartController {
    
    private static Logger logger = LoggerFactory.getLogger(CartController.class);
    
    public static final String ATTRIBUTE_NAME = "cartItem";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;
    
    @Autowired
    private ProductLineRepository productLineRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private Validator validator;
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setAllowedFields("quantity");
    }
    
    @GetMapping
    public String show(Model model){
        Set<CartItem> items = cartService.getAllItem();
        model.addAttribute("items", items);
        return "frontend/shopping/cart";
    }
    
    @GetMapping("/add/{productLineId}")
    public String add(@PathVariable Long productLineId, Model model){
        if(!model.containsAttribute(BINDING_RESULT_NAME)) {
            CartItem cartItem = cartService.getCartItemByProductLine(productLineId);
            if(cartItem == null){
                cartItem = new CartItem();
                ProductLine productLine = productLineRepository.findOne(productLineId);
                cartItem.setProductLine(productLine);
            }
            model.addAttribute(ATTRIBUTE_NAME, cartItem);
        }
        String url = "/shopping-cart/add/"+productLineId;
        return String.format("frontend/fragments/shopping/cart::add(url='%s')",url);
    }
    
    @PostMapping("/add/{productLineId}")
    public String add(
        @PathVariable Long productLineId,
        @ModelAttribute(ATTRIBUTE_NAME) CartItem cartItem, 
        BindingResult bindingResult,
        RedirectAttributes model,
        // SessionStatus lets you clear your SessionAttributes
        SessionStatus sessionStatus){
        String url = "redirect:/shopping-cart/add/"+productLineId;
        Double productPrice = cartItem.getProductLine().getProduct().getPrice();
        cartItem.setTotalPrice(productPrice * cartItem.getQuantity());
        validator.validate(cartItem, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            return url;
        }
        cartService.addItem(cartItem);
        sessionStatus.setComplete();
        return url;
    }
}
