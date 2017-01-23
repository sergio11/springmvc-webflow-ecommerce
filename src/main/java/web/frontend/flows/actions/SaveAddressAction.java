package web.frontend.flows.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import persistence.models.Address;
import persistence.models.User;
import persistence.repositories.AddressRepository;
import persistence.repositories.UserRepository;

/**
 * @author sergio
 */
@Component
public class SaveAddressAction extends AbstractAction {
    
    private static Logger logger = LoggerFactory.getLogger(SaveAddressAction.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AddressRepository addressRepository;

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        MessageContext messageContext = context.getMessageContext();
        MessageBuilder builder = new MessageBuilder();
        try {
            Address address = (Address) context.getFlowScope().get("addressForm");
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            logger.info("Address" + address.toString());
            logger.info("User" + user.toString());
            addressRepository.save(address);
            user.addAddress(address);
            userRepository.save(user);
            messageContext.addMessage(
                    builder.info().source("frontend.checkout.create.address.success"
                            + "").build());
            return success();
        }catch(Exception ex){
            messageContext.addMessage(
                    builder.error().source("frontend.checkout.create.address.failed").build());
            ex.printStackTrace();
            return error();
        }
    }
}
