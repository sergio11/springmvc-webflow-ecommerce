package web.services.impl;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.models.shop.Cart;
import web.models.shop.CartItem;
import web.services.CartService;

/**
 * @author sergio
 */
@Service("cartService")
public class CartServiceImpl implements CartService {
    
    private static Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    
    @Autowired
    private Cart cart;
   
    
    @Override
    public Set<CartItem> getAllItem() {
        return cart.getCartItems();
    }

    @Override
    public void addItem(CartItem item) {
        cart.addItem(item);
    }

    @Override
    public void updateItem(CartItem item) {
       
    }

    @Override
    public void removeAllItems() {
        cart.getCartItems().clear();
    }

    @Override
    public Double getSubtotal() {
        Set<CartItem> items = cart.getCartItems();
        Double subTotal = 0.0;
        for(CartItem item: items){
            subTotal += item.getTotalPrice();
        }
        return subTotal;
    }

    @Override
    public CartItem getCartItemByProductLine(Long id) {
        CartItem cartItemResult = null;
        Set<CartItem> cartItems = cart.getCartItems();
        for(CartItem cartItem: cartItems){
            if(cartItem.getProductLine().getId().equals(id)){
                cartItemResult = cartItem;
                break;
            }
        }
        return cartItemResult;
    }
}
