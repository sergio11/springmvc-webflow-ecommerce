package web.frontend.flows.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import persistence.models.Order;
import persistence.models.OrderLine;
import web.models.shop.CartItem;
import web.services.CartService;

/**
 * Web Flow Action for add all products inside the cart to current order
 * @author sergio
 */
@Component
@Transactional
public class AddProductsToOrderAction extends AbstractAction {
    
    private static Logger logger = LoggerFactory.getLogger(AddProductsToOrderAction.class);
    
    @Autowired
    private CartService cartService;
    
    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        try{
            Order order = (Order)context.getFlowScope().get("order");
            for(CartItem cartItem: cartService.getAllItem()) {
                OrderLine orderLine = new OrderLine();
                orderLine.setOrder(order);
                orderLine.setProductLine(cartItem.getProductLine());
                orderLine.setQuantity(cartItem.getQuantity());
                orderLine.setTotalPrice(cartItem.getTotalPrice());
            }
            logger.info(order.toString());
            // save the order again
            context.getFlowScope().put("order", order);
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
    }
    
}
