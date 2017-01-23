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
import persistence.repositories.OrderRepository;
import persistence.repositories.UserRepository;

/**
 * @author sergio
 */
@Component
public class SaveOrderAction extends AbstractAction {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    protected Event doExecute(RequestContext context) throws Exception {
        try {
            Order order = (Order) context.getFlowScope().get("order");
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            order.setCustomer(user);
            userRepository.save(user);
            orderRepository.save(order);
            return success();
        }catch(Exception ex){
            MessageContext messageContext = context.getMessageContext();
            MessageBuilder builder = new MessageBuilder();
            messageContext.addMessage(
                    builder
                            .error()
                            .source("frontend.checkout.save.order.failed")
                            .build()
            );
            ex.printStackTrace();
            return error();
        }
    }
}
