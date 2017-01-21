package web.frontend.flows.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import persistence.models.Address;
import persistence.models.Order;
import persistence.repositories.AddressRepository;

/**
 * @author sergio
 */
@Component
public class SetBillAddressToOrder extends AbstractAction {
    
    private static Logger logger = LoggerFactory.getLogger(SetBillAddressToOrder.class);
    
    @Autowired
    private AddressRepository addressRepository;

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        try {
            Long idAddressSelected = context.getRequestParameters().getLong("idAddressSelected");
            Order order = (Order) context.getFlowScope().get("order");
            logger.info("idAddressSelected: " + idAddressSelected);
            logger.info("order: " + order.toString());
            Address address = addressRepository.findOne(idAddressSelected);
            logger.info("address: " + address.toString());
            order.setBillTo(address);
            context.getFlowScope().put("order", order);
            return success();
        } catch (Exception e){
            e.printStackTrace();
            return error();
        }
    }
}
