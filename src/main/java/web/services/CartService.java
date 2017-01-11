package web.services;

import java.util.Set;
import web.models.shop.CartItem;

/**
 * @author sergio
 */
public interface CartService {
    Set<CartItem> getAllItem();
    Double getSubtotal();
    void addItem(CartItem item);
    void updateItem(CartItem item);
    void removeAllItems();
}
