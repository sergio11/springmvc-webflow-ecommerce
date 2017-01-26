package web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

/**
 * @author sergio
 */
@ControllerAdvice
public class CommonBindingInitializer {
    
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
     
    @InitBinder
    public void registerCustomEditors(WebDataBinder binder, WebRequest request) {
        String format = messageSource.getMessage("common.date.format", new Object[]{}, Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
