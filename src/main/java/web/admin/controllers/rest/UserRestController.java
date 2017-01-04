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
import web.models.datatables.DataTableUserInput;

@RestController
@RequestMapping("/admin/users")
public class UserRestController {
    
    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);
    
    @Autowired
    private UserRepository userRepository;
   
    @JsonView(DataTablesOutput.View.class)
    @GetMapping("/data")
    public DataTablesOutput<User> all(final @Valid DataTableUserInput input) {
        return userRepository.findAll(input, new UserFilterSpecification(input.getFilter()));
    }
    
}
