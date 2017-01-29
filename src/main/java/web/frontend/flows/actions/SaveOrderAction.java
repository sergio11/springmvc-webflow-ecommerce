package web.frontend.flows.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import persistence.models.Order;
import persistence.models.User;
import persistence.repositories.UserRepository;
import web.services.CartService;

/**
 * @author sergio
 */
@Component
@Transactional
public class SaveOrderAction extends AbstractAction {
    
    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        try {
            Order order = (Order) context.getFlowScope().get("order");
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username);
            order.setCustomer(user);
            // update user information with new order
            userRepository.save(user);
            // remove all items from cart
            cartService.removeAllItems();
            // put the order on the flow again
            context.getFlowScope().put("order", order);
            return success();
        }catch(Exception ex){
            MessageContext messageContext = context.getMessageContext();
            MessageBuilder builder = new MessageBuilder();
            messageContext.addMessage(
                    builder
                            .error()
                            .code("frontend.checkout.save.order.failed")
                            .build()
            );
            ex.printStackTrace();
            return error();
        }
    }
}
