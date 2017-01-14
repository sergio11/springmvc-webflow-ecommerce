package web.frontend.controllers;

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
import persistence.models.ProductLine;
import persistence.repositories.ProductLineRepository;
import web.models.shop.Cart;
import web.models.shop.CartItem;
import web.services.CartService;


/**
 * @author sergio
 */
@Controller
@RequestMapping("/shopping-cart")
@SessionAttributes({ CartController.ATTRIBUTE_CART_ITEM_NAME, CartController.ATTRIBUTE_CART_NAME })
public class CartController {
    
    private static Logger logger = LoggerFactory.getLogger(CartController.class);
    
    public static final String ATTRIBUTE_CART_NAME = "cart";
    public static final String BINDING_CART_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_CART_NAME;
    public static final String ATTRIBUTE_CART_ITEM_NAME = "cartItem";
    public static final String BINDING_CART_ITEM_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_CART_ITEM_NAME;
    
    @Autowired
    private ProductLineRepository productLineRepository;
    
    @Autowired
    private CartService cartService;
    
    @GetMapping
    public String show(Model model){
        if(!model.containsAttribute(BINDING_CART_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_CART_NAME, cartService.getCart());
        }
        return "frontend/shopping/cart";
    }
    
    @PostMapping
    public String save(
        @ModelAttribute(ATTRIBUTE_CART_NAME) Cart cart, 
        BindingResult bindingResult,
        RedirectAttributes model,
        // SessionStatus lets you clear your SessionAttributes
        SessionStatus sessionStatus){
        
        logger.info("Carro de la compra : " + cart.toString());
        String url = "redirect:/shopping-cart";
        if (bindingResult.hasErrors()) {
            model.addFlashAttribute(BINDING_CART_RESULT_NAME, bindingResult);
            return url;
        }
        cartService.updateItems(cart.getCartItems());
        sessionStatus.setComplete();
        return url;
    }
    
    
    @GetMapping("/add/{productLineId}")
    public String add(@PathVariable Long productLineId, Model model){
        if(!model.containsAttribute(BINDING_CART_ITEM_RESULT_NAME)) {
            CartItem cartItem = cartService.getCartItemByProductLine(productLineId);
            if(cartItem == null){
                cartItem = new CartItem();
                ProductLine productLine = productLineRepository.findOne(productLineId);
                cartItem.setProductLine(productLine);
            }
            model.addAttribute(ATTRIBUTE_CART_ITEM_NAME, cartItem);
        }
        String url = "/shopping-cart/add/"+productLineId;
        return String.format("frontend/fragments/shopping/cart::add(url='%s')",url);
    }
    
    @PostMapping("/add/{productLineId}")
    public String add(
        @PathVariable Long productLineId,
        @ModelAttribute(ATTRIBUTE_CART_ITEM_NAME) @Valid CartItem cartItem, 
        BindingResult bindingResult,
        RedirectAttributes model,
        // SessionStatus lets you clear your SessionAttributes
        SessionStatus sessionStatus){
        String url = "redirect:/shopping-cart/add/"+productLineId;
        if (bindingResult.hasErrors()) {
            model.addFlashAttribute(BINDING_CART_ITEM_RESULT_NAME, bindingResult);
            return url;
        }
        cartService.addItem(cartItem);
        sessionStatus.setComplete();
        return url;
    }
}
