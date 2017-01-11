package web.frontend.controllers;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.models.shop.CartItem;
import web.services.CartService;

/**
 * @author sergio
 */
@Controller
@RequestMapping("/shopping-cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @GetMapping
    public String show(Model model){
        Set<CartItem> items = cartService.getAllItem();
        model.addAttribute("items", items);
        return "frontend/shopping/cart";
    }
}
