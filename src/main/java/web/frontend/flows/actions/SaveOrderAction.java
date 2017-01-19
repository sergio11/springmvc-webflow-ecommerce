package web.frontend.flows.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import persistence.models.Address;
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
    protected Event doExecute(RequestContext context) throws Exception {
        try {
            Order order = (Order) context.getFlowScope().get("order");
            User user = (User) context.getFlowScope().get("currentUser");
            order.setCustomer(user);
            userRepository.save(user);
            orderRepository.save(order);
            return success();
        }catch(Exception ex){
            return error();
        }
    }
}
