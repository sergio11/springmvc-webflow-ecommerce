package web.admin.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import persistence.models.User;
import persistence.repositories.UserRepository;
import persistence.specifications.UserFilterSpecification;
import web.models.DataTableUserInput;

@RestController
@RequestMapping("/admin/users")
public class UserRestController {
    
    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        String format = messageSource.getMessage("admin.order.date.format", new Object[]{}, Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    @JsonView(DataTablesOutput.View.class)
    @GetMapping("/data")
    public DataTablesOutput<User> all(final @Valid DataTableUserInput input) {
        return userRepository.findAll(input, new UserFilterSpecification(input.getFilterUser()));
    }
    
}
