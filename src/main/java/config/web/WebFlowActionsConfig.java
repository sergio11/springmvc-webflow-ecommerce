package config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.models.Address;
import web.frontend.flows.actions.AddressFormAction;

/**
 * Web Flow Bean Actions
 * @author sergio
 */
@Configuration
public class WebFlowActionsConfig {
    
    /**
     * Bean for create Address on Flow Scope
     * @return AddressFormAction
     */
    @Bean(name = "addressFormAction")
    public AddressFormAction provideAddressFormAction(){
        AddressFormAction addressFormAction = new AddressFormAction();
        addressFormAction.setFormObjectClass(Address.class);
        addressFormAction.setFormObjectName("addressForm");
        return addressFormAction;
    }
    
}
