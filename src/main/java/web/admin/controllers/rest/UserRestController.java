package web.admin.controllers.rest;

import com.fasterxml.jackson.annotation.JsonView;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import persistence.models.User;
import persistence.repositories.UserRepository;
import persistence.specifications.UserFilterSpecification;
import security.CurrentUserAttached;
import web.models.datatables.DataTableUserInput;
import web.models.datatables.FilterUser;

@RestController
@RequestMapping("/admin/users")
public class UserRestController {
    
    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);
    
    @Autowired
    private UserRepository userRepository;
   
    @JsonView(DataTablesOutput.View.class)
    @GetMapping("/data")
    public DataTablesOutput<User> all(final @CurrentUserAttached User currentUser, final @Valid DataTableUserInput input) {
        FilterUser filterUser = input.getFilter();
        if(currentUser != null){
            logger.info(currentUser.toString());
            filterUser.setCurrentUser(currentUser.getId());
        }
        return userRepository.findAll(input, new UserFilterSpecification(filterUser));
    }
    
}
