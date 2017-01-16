package web.frontend.flows.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import persistence.models.Address;
import persistence.models.Order;
import persistence.repositories.AddressRepository;

/**
 * @author sergio
 */
public class SetBillAddressToOrder extends AbstractAction {
    
    @Autowired
    private AddressRepository addressRepository;

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        try {
            Long idAddressSelected = (Long) context.getFlowScope().get("idAddressSelected");
            Order order = (Order) context.getFlowScope().get("order");
            Address address = addressRepository.findOne(idAddressSelected);
            order.setBillTo(address);
            context.getFlowScope().put("order", order);
            return success();
        } catch (Exception e){
            return error();
        }
    }
}
