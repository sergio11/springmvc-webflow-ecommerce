package web.frontend.flows.actions;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.action.FormAction;
import persistence.models.Country;
import web.editors.CountryEditor;

/**
 * @author sergio
 */

public class AddressFormAction extends FormAction {
    
    @Autowired
    private CountryEditor countryEditor;

    @Override
    protected void registerPropertyEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Country.class, countryEditor);
    }
}
