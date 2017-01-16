package web.frontend.flows.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import persistence.models.Address;
import persistence.models.User;
import persistence.repositories.UserRepository;

/**
 * @author sergio
 */
public class AddressAction extends AbstractAction {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        try {
            Address address = (Address) context.getFlowScope().get("address");
            User user = (User) context.getFlowScope().get("currentUser");
            user.addAddress(address);
            userRepository.save(user);
            return success();
        }catch(Exception ex){
            return error();
        }
    }
}
