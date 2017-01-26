package web.editors;

import java.beans.PropertyEditorSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import persistence.models.Country;
import persistence.repositories.CountryRepository;

/**
 * @author sergio
 */
@Component
public class CountryEditor extends PropertyEditorSupport {
    
    private static Logger logger = LoggerFactory.getLogger(CountryEditor.class);
    
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        logger.info("Country code: " + text);
        Country country = null;
        if (text != null && !text.isEmpty()) {
            country = countryRepository.findOne(Long.valueOf(text));
        }
        this.setValue(country);
    }

    @Override
    public String getAsText() {
        Country c =  (Country)this.getValue();
        return c != null ? c.getCode() : null;
    }
    
}
