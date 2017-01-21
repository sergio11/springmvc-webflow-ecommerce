package web.frontend.flows.actions;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.RequestContext;
import persistence.models.Address;
import persistence.models.Country;
import web.editors.CountryEditor;

/**
 * @author sergio
 */
@Component
public class AddressFormAction extends FormAction {
    
    @Autowired
    private CountryEditor countryEditor;

    @Override
    protected Object createFormObject(RequestContext context) throws Exception {
        Address address = (Address) context.getFlowScope().get("address");
        return address;
    }

    @Override
    protected void registerPropertyEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Country.class, countryEditor);
    }
}
